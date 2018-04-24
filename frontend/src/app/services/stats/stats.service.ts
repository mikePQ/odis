import {Injectable} from '@angular/core';
import {Stats, StatsType} from '../../domain/stats';

@Injectable()
export class StatsService {

  constructor() {
  }

  getStats(statsType: StatsType): Stats {
    switch (statsType) {
      case StatsType.Daily:
        return this.getDailyStats();
      case StatsType.Weekly:
        return this.getWeeklyStats();
      case  StatsType.Monthly:
        return this.getMonthlyStats();
    }
  }

  private getDailyStats(): Stats {
    throw new Error();
  }

  private getWeeklyStats(): Stats {
    throw new Error();
  }

  private getMonthlyStats(): Stats {
    throw new Error();
  }
}
