import {Injectable} from '@angular/core';
import {BytesPerRange, Stats, StatsType} from '../../domain/stats';
import {HttpClient} from '@angular/common/http';
import {api} from '../../api';
import {Observable} from 'rxjs/Observable';
import {Host} from '../../domain/host';

@Injectable()
export class StatsService {

  constructor(private httpClient: HttpClient) {
  }

  getBytesPerRange(numberOfRanges: Number, timestampBegin: Number, timestampEnd: Number) : Observable<Array<BytesPerRange>> {
    const bytesProcessedApi: string = api.getBytesProcessed(numberOfRanges, timestampBegin, timestampEnd);
    console.log(bytesProcessedApi);
    return this.httpClient.get<BytesPerRange[]>(bytesProcessedApi);
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
