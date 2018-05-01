import {Injectable} from '@angular/core';
import {BytesPerRange, Stats, StatsQueryParams, StatsType, TrafficType} from '../../domain/stats';
import {HttpClient} from '@angular/common/http';
import {api} from '../../api';
import {Observable} from 'rxjs/Observable';
import {toTimestamp} from '../../Utils';
import {map} from 'rxjs/operators';

@Injectable()
export class StatsService {

  constructor(private httpClient: HttpClient) {
  }

  getBytesPerRange(numberOfRanges: Number, timestampBegin: Number, timestampEnd: Number): Observable<Array<BytesPerRange>> {
    const bytesProcessedApi: string = api.getBytesProcessed(numberOfRanges, timestampBegin, timestampEnd);
    return this.httpClient.get<BytesPerRange[]>(bytesProcessedApi);
  }

  getStats(params: StatsQueryParams): Observable<Stats> {
    let requestUrl = api.getBytesProcessed(params.granularity, toTimestamp(params.start), toTimestamp(params.end));
    if (params.hostIp) {
      requestUrl += `&hostIp=${params.hostIp}`;
    }

    if (params.trafficType != TrafficType.All) {
      let value = params.trafficType == TrafficType.In ? 'to' : 'from';
      requestUrl += `&direction=${value}`;
    }

    if (params.peersIps.length > 0) {
      for (let peerIp of params.peersIps) {
        requestUrl += `&peersIps=${peerIp}`;
      }
    }

    return this.httpClient.get<BytesPerRange[]>(requestUrl).pipe(map(elements => {
      return new Stats(toTimestamp(params.start), toTimestamp(params.end), elements, params.trafficType, params.hostIp, params.peersIps);
    }));
  }

  static getDefaultStats(): Stats {
    let timestamp = toTimestamp(new Date());
    return new Stats(timestamp, timestamp, []);
  }

  static getDefaultQueryParams(statsType: StatsType): StatsQueryParams {
    switch (statsType) {
      case StatsType.Daily:
        return StatsService.defaultDailyStatsQueryParams();
      case StatsType.Weekly:
        return StatsService.defaultWeeklyStatsQueryParams();
      case StatsType.Monthly:
        return StatsService.defaultMonthlyStatsQueryParams();
    }
  }

  private static defaultDailyStatsQueryParams(): StatsQueryParams {
    const endDate = new Date();
    const beginDate = new Date(endDate.getTime());
    beginDate.setHours(beginDate.getHours() - 11);
    const granulation = 12;

    return new StatsQueryParams(beginDate, endDate, granulation);
  }

  private static defaultWeeklyStatsQueryParams(): StatsQueryParams {
    const endDate = new Date();
    const beginDate = new Date(endDate.getTime());
    beginDate.setDate(beginDate.getDate() - 6);
    const granulation = 7;

    return new StatsQueryParams(beginDate, endDate, granulation);
  }

  private static defaultMonthlyStatsQueryParams(): StatsQueryParams {
    const endDate = new Date();
    const beginDate = new Date(endDate.getTime());
    beginDate.setDate(beginDate.getDate() - 29);
    const granulation = 30;

    return new StatsQueryParams(beginDate, endDate, granulation);
  }
}
