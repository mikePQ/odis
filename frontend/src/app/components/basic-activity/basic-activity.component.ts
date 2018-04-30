import {Component, OnInit} from '@angular/core';
import {DatePipe} from '@angular/common';
import {StatsService} from '../../services/stats/stats.service';
import {BytesPerRangeParameters} from '../../domain/stats';
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
  dailyChartLabels: Array<string>;
  weeklyChartLabels: Array<string>;
  monthlyChartLabels: Array<string> = [];

  showDetailedChart = false;

  detailedValues = [];
  detailedChartLabels: Array<string> = [];

  dailyValues = [];
  weeklyValues = [];
  monthlyValues = [];

  constructor(private statsService: StatsService,
              private modalService: NgbModal) {
  }

  ngOnInit() {
    this.getDailyValues();
    this.getWeeklyValues();
    this.getMonthlyValues();
  }

  changeChart(type: ChartType) {
    this.currentChart = type;
    this.showDetailedChart = false;
  }

  getBytesProcessedInRange(bytesPerRangeParameters: BytesPerRangeParameters) {
    this.statsService.getBytesPerRange(bytesPerRangeParameters.granularity,
      BasicActivityComponent.toTimestamp(bytesPerRangeParameters.begin),
      BasicActivityComponent.toTimestamp(bytesPerRangeParameters.end))
      .subscribe(bytesPerRange => {
        this.detailedChartLabels = BasicActivityComponent.getChartLabels(bytesPerRange
          .map(bytesInRange => new Date(bytesInRange.begin * 1000)));
        this.detailedValues = bytesPerRange.map(bytesInRange => bytesInRange.bytes);
        this.showDetailedChart = true;
      });
  }

  isCurrentChart(type: ChartType) {
    return this.currentChart === type;
  }

  getDailyValues(): void {
    const date = new Date();
    const endTimestamp = BasicActivityComponent.toTimestamp(date);
    date.setHours(date.getHours() - 11);
    const beginTimestamp = BasicActivityComponent.toTimestamp(date);
    const granulation = 12;
    this.statsService.getBytesPerRange(granulation, beginTimestamp, endTimestamp)
      .subscribe(bytesPerRange => {
        this.dailyChartLabels = BasicActivityComponent.getChartLabels(bytesPerRange
          .map(bytesInRange => new Date(bytesInRange.begin * 1000)));
        this.dailyValues = bytesPerRange.map(bytesInRange => bytesInRange.bytes);
      });
  }

  getWeeklyValues() {
    const date = new Date();
    const endTimestamp = BasicActivityComponent.toTimestamp(date);
    date.setDate(date.getDate() - 6);
    const beginTimestamp = BasicActivityComponent.toTimestamp(date);
    const granulation = 7;
    this.statsService.getBytesPerRange(granulation, beginTimestamp, endTimestamp)
      .subscribe(bytesPerRange => {
        this.weeklyChartLabels = BasicActivityComponent.getChartLabels(bytesPerRange
          .map(bytesInRange => new Date(bytesInRange.begin * 1000)));
        this.weeklyValues = bytesPerRange.map(bytesInRange => bytesInRange.bytes);
      });
  }

  getMonthlyValues() {
    const date = new Date();
    const endTimestamp = BasicActivityComponent.toTimestamp(date);
    date.setDate(date.getDate() - 29);
    const beginTimestamp = BasicActivityComponent.toTimestamp(date);
    const granulation = 30;
    this.statsService.getBytesPerRange(granulation, beginTimestamp, endTimestamp)
      .subscribe(bytesPerRange => {
        this.monthlyChartLabels = BasicActivityComponent.getChartLabels(bytesPerRange
          .map(bytesInRange => new Date(bytesInRange.begin * 1000)));
        this.monthlyValues = bytesPerRange.map(bytesInRange => bytesInRange.bytes);
      });
  }

  open() {
    const modalRef = this.modalService.open(DateInputModalComponent);
    modalRef.componentInstance.notify.subscribe((bytesPerRangeParameters) => {
      this.getBytesProcessedInRange(bytesPerRangeParameters);
    });
  }

  static getChartLabels(datesToTransform: Array<Date>): Array<string> {
    let datePipe = new DatePipe('en');
    let dates = [];
    console.log(datesToTransform);
    datesToTransform.forEach(date => dates.push(datePipe.transform(date, 'short')));
    return dates;
  }

  static toTimestamp(date: Date): number {
    return Math.round(date.getTime() / 1000);
  }
}

enum ChartType {
  Daily = 0, Weekly = 1, Monthly = 2
}
