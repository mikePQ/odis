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
  dailyChartLabels: Array<string>;
  weeklyChartLabels: Array<string>;
  monthlyChartLabels = Array<string>;

  showDetailedChart = false;

  detiledValues = [];
  detailedChartLabels = Array<string>;

  dailyValues = [];
  weeklyValues = [];
  monthlyValues = [];

  constructor(private statsService: StatsService, private modalService: NgbModal) {
  }

  ngOnInit() {
    this.getDailyValues();
    this.getWeeklyValues();
    this.getMonthyValues();
  }

  changeChart(type: ChartType) {
    this.currentChart = type;
    this.showDetailedChart = false;
  }

  getBytesProcessedInRange(bytesPerRangeParameters: BytesPerRangeParameters) {
    console.log(bytesPerRangeParameters);
    this.statsService.getBytesPerRange(bytesPerRangeParameters.granularity,
      Math.round(bytesPerRangeParameters.begin.getTime() / 1000),
      Math.round(bytesPerRangeParameters.end.getTime() / 1000))
      .subscribe(bytesPerRange => {
        this.detailedChartLabels = BasicActivityComponent.getChartLabels(bytesPerRange
          .map( bytesInRange => new Date(bytesInRange.begin * 1000)));
        this.detiledValues = bytesPerRange.map( bytesInRange => bytesInRange.bytes);
        this.showDetailedChart = true;
        console.log(this.detailedChartLabels);
      });
  }

  isCurrentChart(type: ChartType) {
    return this.currentChart === type;
  }

  static getChartLabels(datesToTransform: Array<Date>): Array<string> {
    let datePipe = new DatePipe('en');
    let dates = [];
    console.log(datesToTransform);
      datesToTransform.forEach(date =>  dates.push(datePipe.transform(date, 'short')))
    return dates;
  }
  getDailyValues(): void {
    const date = new Date();
    const endTimestamp = Math.round(date.getTime() / 1000);
    date.setHours(date.getHours() - 11);
    const beginTimestamp = Math.round(date.getTime() / 1000);
    const granulation = 12;
    this.statsService.getBytesPerRange(granulation, beginTimestamp, endTimestamp)
      .subscribe(bytesPerRange => {
        this.dailyChartLabels = BasicActivityComponent.getChartLabels(bytesPerRange
          .map( bytesInRange => new Date(bytesInRange.begin * 1000)));
        this.dailyValues = bytesPerRange.map( bytesInRange => bytesInRange.bytes);
      });
  }

  getWeeklyValues(): void {
    const date = new Date();
    const endTimestamp = Math.round(date.getTime() / 1000);
    date.setDate(date.getDate() - 6);
    const beginTimestamp = Math.round(date.getTime() / 1000);
    const granulation = 7;
    this.statsService.getBytesPerRange(granulation, beginTimestamp, endTimestamp)
      .subscribe(bytesPerRange => {
        this.weeklyChartLabels = BasicActivityComponent.getChartLabels(bytesPerRange
          .map( bytesInRange => new Date(bytesInRange.begin * 1000)));
        this.weeklyValues = bytesPerRange.map( bytesInRange => bytesInRange.bytes);
      });
  }

  getMonthyValues(): void {
    const date = new Date();
    const endTimestamp = Math.round(date.getTime() / 1000);
    date.setDate(date.getDate() - 29);
    const beginTimestamp = Math.round(date.getTime() / 1000);
    const granulation = 30;
    this.statsService.getBytesPerRange(granulation, beginTimestamp, endTimestamp)
      .subscribe(bytesPerRange => {
        this.monthlyChartLabels = BasicActivityComponent.getChartLabels(bytesPerRange
          .map( bytesInRange => new Date(bytesInRange.begin * 1000)));
        this.monthlyValues = bytesPerRange.map( bytesInRange => bytesInRange.bytes);
      });
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
