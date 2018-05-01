import {DatePipe} from '@angular/common';

export function toTimestamp(date: Date): number {
  return Math.round(date.getTime() / 1000);
}

export function toDate(timestamp: number): Date {
  return new Date(timestamp * 1000);
}

export function asStrings(dates: Array<Date>): Array<string> {
  let datePipe = new DatePipe('en');
  let result = [];
  dates.forEach(date => result.push(datePipe.transform(date, 'short')));
  return result;
}
