export class Stats {
  constructor(public startTimestamp: number,
              public endTimestamp: number,
              public bytes: number,
              public trafficType: TrafficType = TrafficType.All,
              public hostIp: string = '',
              public peersIps: Array<string> = []) {
  }
}

export class StatsQueryParams {
  constructor(public start: Date = new Date(),
              public end: Date = new Date(),
              public trafficType: TrafficType = TrafficType.All,
              public hostIp: string = '',
              public peersIps: Array<string> = []) {
  }
}

export enum StatsType {
  Daily, Weekly, Monthly
}

export enum TrafficType {
  In, Out, All
}

export class BytesPerRange {
  constructor(public begin: number,
              public end: number,
              public bytes: number) {
  }
}

export class BytesPerRangeParameters {
  constructor(public begin: Date = new Date(),
              public end: Date = new Date(),
              public granularity: number = 10) {
  }
}
