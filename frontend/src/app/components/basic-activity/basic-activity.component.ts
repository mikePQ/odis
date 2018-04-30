import {Component, OnInit} from '@angular/core';
import {DatePipe} from '@angular/common';
import {StatsService} from '../../services/stats/stats.service';
import {BytesPerRange, BytesPerRangeParameters} from '../../domain/stats';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DateInputModalComponent} from '../date-input-modal/date-input-modal.component';

@Component({
  selector: 'app-basic-activity',
  templateUrl: './basic-activity.component.html',
  styleUrls: ['./basic-activity.component.css']
})
export class BasicActivityComponent implements OnInit {

  chartType = ChartType;
  currentChart = ChartType.Daily;
  dailyChartLabels = Array<string>;
  weeklyChartLabels = BasicActivityComponent.getWeeklyChartLabels();
  monthlyChartLabels = BasicActivityComponent.getMonthlyChartLabels();

  bytesPerRange: Array<BytesPerRange>;

  dailyValues = [];
  weeklyValues = [5, 3, 6, 5, 1, 2, 4];
  monthlyValues = [5, 3, 6, 5, 1, 2, 4, 5, 3, 6, 5, 1, 2, 4, 5, 3, 6, 5, 1, 2, 4, 5, 3, 6, 5, 1, 2, 4, 2, 3, 5];

  constructor(private statsService: StatsService, private modalService: NgbModal) {
  }

  ngOnInit() {
    this.getDailyValues();
  }

  changeChart(type: ChartType) {
    this.currentChart = type;
  }

  getBytesProcessedInRange(bytesPerRangeParameters: BytesPerRangeParameters) {
    this.statsService.getBytesPerRange(bytesPerRangeParameters.granularity, bytesPerRangeParameters.begin.getTime() / 1000,
      bytesPerRangeParameters.end.getTime() / 1000)
      .subscribe(bytesPerRange => this.bytesPerRange = bytesPerRange);
  }

  isCurrentChart(type: ChartType) {
    return this.currentChart === type;
  }

  static getChartLabels(datesToTransform: Array<Date>): Array<string> {
    let datePipe = new DatePipe('en');
    let dates = [];
    datesToTransform.forEach(date =>       dates.push(datePipe.transform(date, 'short')))
    return dates;
  }

  static getWeeklyChartLabels(): Array<string> {
    let datePipe = new DatePipe('en');
    let dates = [];
    for (let i = 6; i >= 0; i--) {
      let date = new Date();
      date.setDate(date.getDate() - i);
      dates.push(datePipe.transform(date, 'mediumDate'));
    }

    return dates;
  }

  getDailyValues(): void {
    const date = new Date();
    const firstDayTimestamp = date.getTime() / 1000;
    date.setDate(date.getHours() - 11);
    const lastDayTimestamp = date.getTime() / 1000;
    const granulation = 12;
    this.statsService.getBytesPerRange(granulation, firstDayTimestamp, lastDayTimestamp)
      .subscribe(bytesPerRange => {
        this.dailyChartLabels = BasicActivityComponent.getChartLabels(bytesPerRange
          .map( bytesInRange => new Date(bytesInRange.begin.toString())));
        this.dailyValues = bytesPerRange.map( bytesInRange => bytesInRange.bytes);
      });
  }

  static getMonthlyChartLabels(): Array<string> {
    let datePipe = new DatePipe('en');
    let dates = [];
    for (let i = 29; i >= 0; i--) {
      let date = new Date();
      date.setDate(date.getDate() - i);
      dates.push(datePipe.transform(date, 'mediumDate'));
    }

    return dates;
  }

  open(content) {
    const modalRef = this.modalService.open(DateInputModalComponent);
    modalRef.componentInstance.notify.subscribe((bytesPerRangeParameters) => {
      console.log(bytesPerRangeParameters);
      this.getBytesProcessedInRange(bytesPerRangeParameters);
    });
  }
}

enum ChartType {
  Daily = 0, Weekly = 1, Monthly = 2
}
