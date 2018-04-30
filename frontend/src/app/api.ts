import {BytesPerRange} from './domain/stats';

export class Api {
  private baseUrl: string = 'http://localhost:9090/api';

  getHosts(): string {
    return `${this.baseUrl}/hosts`;
  }

  getBytesProcessed(numberOfRanges: Number, timestampBegin: Number, timestampEnd: Number): string {
    return `${this.baseUrl}/activities/bytes?fromTime=${timestampBegin}&toTime=${timestampEnd}&limit=${numberOfRanges}`;
  }
}

export const api = new Api();
