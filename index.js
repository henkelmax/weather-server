// @ts-check
const express = require('express');
const cors = require('cors');
const nocache = require("nocache");
const Joi = require('joi');
const { MongoClient } = require('mongodb');
const auth = require('basic-auth');
const moment = require('moment-timezone');
const path = require('path');
const { renderSVG } = require(path.join(__dirname, 'widget', 'index.js'));

const dbIp = process.env.DB_IP || 'localhost';
const dbPort = Number.parseInt(process.env.DB_PORT, 10) || 27017;
const dbUrl = `mongodb://${dbIp}:${dbPort}`;
const dbName = process.env.DB_NAME || 'weather';

const port = Number.parseInt(process.env.PORT, 10) || 8089;

const loginUsername = process.env.LOGIN_USERNAME;
const loginPassword = process.env.LOGIN_PASSWORD;

const apiKey = process.env.API_KEY || 'f943db12-9f0c-40f9-acf3-ff2339b51c74';

const dayMilliseconds = 24 * 60 * 60 * 1000;

const apiKeySchema = Joi.string().regex(/[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}/).required();

const passKeySchema = Joi.object()
    .keys({
        passKey: Joi.string()
            .regex(/^[0-9a-fA-F]{32}$/)
            .required(),
        deviceId: Joi.number()
            .integer()
            .min(1)
            .required()
    })
    .required();

const deviceIdSchema = Joi.number()
    .integer()
    .min(1)
    .default(1);

const fromTimestampSchema = Joi.number()
    .integer()
    .min(1)
    .default(() => new Date().getTime() - dayMilliseconds);

const toTimestampSchema = Joi.number()
    .integer()
    .min(1)
    .default(() => new Date().getTime());

const ecowittSchema = Joi.object()
    .keys({
        PASSKEY: Joi.string()
            .regex(/^[0-9a-fA-F]{32}$/)
            .required(),
        dateutc: Joi.string()
            .regex(/^\d{4}-\d{1,2}-\d{1,2} \d{1,2}:\d{1,2}:\d{1,2}$/)
            .required(),
        tempf: Joi.number()
            .required(),
        humidity: Joi.number()
            .required(),
        winddir: Joi.number()
            .required(),
        windspeedmph: Joi.number()
            .required(),
        windgustmph: Joi.number()
            .required(),
        maxdailygust: Joi.number()
            .required(),
        rainratein: Joi.number()
            .required(),
        eventrainin: Joi.number()
            .required(),
        hourlyrainin: Joi.number()
            .required(),
        dailyrainin: Joi.number()
            .required(),
        weeklyrainin: Joi.number()
            .required(),
        monthlyrainin: Joi.number()
            .required(),
        solarradiation: Joi.number()
            .required(),
        uv: Joi.number()
            .required(),
        baromrelin: Joi.number()
            .required(),
        baromabsin: Joi.number()
            .required()
    })
    .unknown()
    .required();

(async () => {
    const client = await MongoClient.connect(dbUrl, {
        useNewUrlParser: true,
        useUnifiedTopology: true
    });

    const db = client.db(dbName);

    const app = express();

    app.set('trust proxy', true)
    app.use(express.urlencoded({ extended: false }));
    app.use(express.json());
    app.use(cors());
    app.use(nocache());
    app.all('/', async (req, res, next) => {
        if (req.secure) {
            return next();
        }
        if (req.hostname === 'localhost' || req.hostname === '127.0.0.1') {
            return next();
        }
        res.redirect(`https://${req.hostname}${req.originalUrl}`);
    });

    app.get('/', async (req, res, next) => {
        if (await basicAuth(req)) {
            next();
        } else {
            res.set('WWW-Authenticate', 'Basic realm="401"');
            res.status(401).send('Authentication required.');
        }
    });

    app.use('/', express.static(path.join(__dirname, 'frontend/dist')));

    app.get('/api/v1/widget/weather.svg', async (req, res) => {
        const deviceIdElement = deviceIdSchema.validate(req.query.id);
        if (deviceIdElement.error) {
            res.status(400).send({ error: deviceIdElement.error.details });
            res.end();
            return;
        }

        const cursor = db.collection('weather').aggregate([
            { $match: { deviceId: deviceIdElement.value } },
            { $sort: { date: -1 } },
            { $limit: 1 }
        ]);

        if (!await cursor.hasNext()) {
            res.status(404).send({ error: 'No element found' });
            res.end();
            return;
        }

        res.setHeader('Content-Type', 'image/svg+xml');
        res.status(200).send(renderSVG(await cursor.next()));
        cursor.close();
    });

    app.get('/api/v1/weather', async (req, res) => {
        const fromElement = fromTimestampSchema.validate(req.query.from);
        if (fromElement.error) {
            res.status(400).send({ error: fromElement.error.details });
            res.end();
            return;
        }

        const toElement = toTimestampSchema.validate(req.query.to);
        if (toElement.error) {
            res.status(400).send({ error: toElement.error.details });
            res.end();
            return;
        }

        if (toElement.value - fromElement.value > dayMilliseconds) {
            res.status(400).send({ error: 'The maximum timespan is 24 hours' });
            res.end();
            return;
        }

        const deviceIdElement = deviceIdSchema.validate(req.query.id);
        if (deviceIdElement.error) {
            res.status(400).send({ error: deviceIdElement.error.details });
            res.end();
            return;
        }

        const fromDate = new Date(fromElement.value);
        const toDate = new Date(toElement.value);

        const weather = await db.collection('weather').aggregate([
            { $match: { date: { $gte: fromDate, $lte: toDate }, deviceId: deviceIdElement.value } },
            { $sort: { date: 1 } }
        ]).toArray();
        res.status(200).send(weather);
    });

    app.get('/api/v1/weather/current', async (req, res) => {
        const deviceIdElement = deviceIdSchema.validate(req.query.id);
        if (deviceIdElement.error) {
            res.status(400).send({ error: deviceIdElement.error.details });
            res.end();
            return;
        }

        const cursor = db.collection('weather').aggregate([
            { $match: { deviceId: deviceIdElement.value } },
            { $sort: { date: -1 } },
            { $limit: 1 }
        ]);

        if (!await cursor.hasNext()) {
            res.status(404).send({ error: 'No element found' });
            res.end();
            return;
        }

        res.status(200).send(await cursor.next());
    });

    app.post(['/api/v1/weather/ecowitt', '/data/ecowitt'], async (req, res) => {
        const ecowittElement = ecowittSchema.validate(req.body);
        if (ecowittElement.error) {
            res.status(400).send({ error: ecowittElement.error.details });
            res.end();
            return;
        }

        const pk = await db.collection('passkeys').findOne({ passKey: ecowittElement.value.PASSKEY });

        if (!pk || !pk.deviceId) {
            res.status(400).send({ error: 'PassKey not found' });
            res.end();
            return;
        }

        const weatherData = {
            deviceId: pk.deviceId,
            date: moment.utc(ecowittElement.value.dateutc).local().toDate(),
            temperature: +toCelsius(ecowittElement.value.tempf).toFixed(2),
            humidity: ecowittElement.value.humidity,
            relativePressure: +toHpa(ecowittElement.value.baromrelin).toFixed(2),
            absolutePressure: +toHpa(ecowittElement.value.baromabsin).toFixed(2),
            windDirection: ecowittElement.value.winddir,
            windSpeed: +toKmh(ecowittElement.value.windspeedmph).toFixed(2),
            windGust: +toKmh(ecowittElement.value.windgustmph).toFixed(2),
            maxDailyWindGust: +toKmh(ecowittElement.value.maxdailygust).toFixed(2),
            rainRate: +toMm(ecowittElement.value.rainratein).toFixed(2),
            rainEvent: +toMm(ecowittElement.value.eventrainin).toFixed(2),
            rainHourly: +toMm(ecowittElement.value.hourlyrainin).toFixed(2),
            rainDaily: +toMm(ecowittElement.value.dailyrainin).toFixed(2),
            rainWeekly: +toMm(ecowittElement.value.weeklyrainin).toFixed(2),
            rainMonthly: +toMm(ecowittElement.value.monthlyrainin).toFixed(2),
            solarRadiation: ecowittElement.value.solarradiation,
            uvi: ecowittElement.value.uv,
        }

        const result = await db.collection('weather').insertOne(weatherData);
        if (!result.acknowledged) {
            res.status(400).send({ error: [{ message: 'Unknown Error' }] });
            return;
        }
        res.status(200).end();
    });

    app.post('/data/passkey/add', async (req, res) => {
        const passKeyElement = passKeySchema.validate(req.body)
        if (passKeyElement.error) {
            res.status(400).send({ error: passKeyElement.error.details });
            res.end();
            return;
        }
        //TODO check duplicate passkeys
        if (!checkAuthRequest(req)) {
            res.status(400).send({ error: 'Invalid API key' });
            res.end();
            return;
        }

        const result = await db.collection('passkeys').insertOne(passKeyElement.value);
        if (!result.acknowledged) {
            res.status(400).send({ error: [{ message: 'Unknown Error' }] });
            return;
        }
        res.status(200).end();
    });

    app.listen(port, () => {
        console.log(`Server listening on port ${port}`);
    });

})();

function toCelsius(fahrenheit) {
    return (fahrenheit - 32) * 5 / 9;
}

function toHpa(inhg) {
    return inhg * 33.86389;
}

function toKmh(mph) {
    return mph * 1.609344;
}

function toMm(inch) {
    return inch * 25.4;
}

function checkAuthRequest(req) {
    const apiKeyElement = apiKeySchema.validate(req.headers['x-api-key']);
    if (apiKeyElement.error) {
        return false;
    }
    return apiKey === apiKeyElement.value;
}

function basicAuth(req) {
    const credentials = auth(req);
    if (!loginUsername || !loginPassword) {
        return true;
    }
    return credentials && credentials.name === loginUsername && credentials.pass === loginPassword;
}