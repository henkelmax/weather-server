// @ts-check
const fs = require('fs');
const path = require('path');
const ejs = require('ejs');
const moment = require('moment');

const svgTemplate = fs.readFileSync(path.join(__dirname, 'assets', 'test.svg.ejs'));
const rainIcon = fs.readFileSync(path.join(__dirname, 'assets', 'rain_icon.svg'));
const sunIcon = fs.readFileSync(path.join(__dirname, 'assets', 'sun_icon.svg'));
const updateIcon = fs.readFileSync(path.join(__dirname, 'assets', 'update_icon.svg'));
const cloudyImage = fs.readFileSync(path.join(__dirname, 'assets', 'cloudy.svg'));
const rainyImage = fs.readFileSync(path.join(__dirname, 'assets', 'rainy.svg'));
const sunnyCloudyImage = fs.readFileSync(path.join(__dirname, 'assets', 'sunny_cloudy.svg'));
const sunnyImage = fs.readFileSync(path.join(__dirname, 'assets', 'sunny.svg'));

let template = ejs.compile(svgTemplate.toString(), {});

const images = {
    rainIcon: rainIcon,
    sunIcon: sunIcon,
    updateIcon: updateIcon,
    cloudy: cloudyImage,
    rainy: rainyImage,
    sunnyCloudy: sunnyCloudyImage,
    sunny: sunnyImage
}

function renderSVG(weather) {
    return template({
        moment, weather, images
    });
}

module.exports = { renderSVG };