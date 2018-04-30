export class Stats {
  constructor(public date: Date,
              public statsType: StatsType,
              public bytes: number) {}
}

export enum StatsType {
  Daily, Weekly, Monthly
}

export class BytesPerRange {
  constructor(public begin: number,
              public end: number,
              public bytes: number) {}
}

export class BytesPerRangeParameters {
  constructor(public begin: Date = new Date(),
              public end: Date = new Date(),
              public granularity: number = 10) {}
}
