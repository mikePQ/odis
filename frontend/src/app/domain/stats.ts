export class Stats {
  constructor(public date: Date,
              public statsType: StatsType,
              public bytes: number) {}
}

export enum StatsType {
  Daily, Weekly, Monthly
}
