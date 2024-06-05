type TimeSeries = {
    name: string,
    unit?: string,
    color: string,
    data: TimeSeriesEntry[]
}

type TimeSeriesEntry = {
    x: Date,
    y: number,
    tooltipFooter?: string
}