export class Stats {
  constructor(public date: Date,
              public statsType: StatsType,
              public bytes: number) {}
}

export enum StatsType {
  Daily, Weekly, Monthly
}

export class BytesPerRange {
  constructor(public begin: Number,
              public end: Number,
              public bytes: Number) {}
}

export class BytesPerRangeParameters {
  constructor(public begin: Date = new Date(),
              public end: Date = new Date(),
              public granularity: Number = 0) {}
}
