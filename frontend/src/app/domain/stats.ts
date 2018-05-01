import {asStrings, toDate} from '../Utils';

export class Stats {
  constructor(public startTimestamp: number,
              public endTimestamp: number,
              public entries: BytesPerRange[],
              public trafficType: TrafficType = TrafficType.All,
              public hostIp: string = '',
              public peersIps: Array<string> = []) {
  }

  totalBytes = this.entries.map(elem => elem.bytes)
    .reduce((a, b) => a + b, 0);

  getTimestamps(): Array<string> {
    return asStrings(this.entries.map(bytesInRange => toDate(bytesInRange.begin)));
  }

  getValues(): Array<number> {
    return this.entries.map(bytesInRange => bytesInRange.bytes);
  }
}

export class StatsQueryParams {
  constructor(public start: Date = new Date(),
              public end: Date = new Date(),
              public granularity: number,
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
