export function max<T>(data: T[], func: (e: T) => number): number {
    return Math.max(...data.map((e) => func(e)));
}

export function min<T>(data: T[], func: (e: T) => number): number {
    return Math.min(...data.map((e) => func(e)));
}

export function round(n: number): number {
    return Math.round(n * 10) / 10
}