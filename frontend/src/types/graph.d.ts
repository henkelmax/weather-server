type TimeSeries = {
    name: string,
    unit: string|null,
    color: string,
    data: TimeSeriesEntry[]
}

type TimeSeriesEntry = {
    x: Date,
    y: number
}