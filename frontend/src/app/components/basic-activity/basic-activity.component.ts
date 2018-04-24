import {Component, OnInit} from '@angular/core';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-basic-activity',
  templateUrl: './basic-activity.component.html',
  styleUrls: ['./basic-activity.component.css']
})
export class BasicActivityComponent implements OnInit {

  chartType = ChartType;
  currentChart = ChartType.Daily;

  dailyChartLabels = BasicActivityComponent.getDailyChartLabels();
  weeklyChartLabels = BasicActivityComponent.getWeeklyChartLabels();
  monthlyChartLabels = BasicActivityComponent.getMonthlyChartLabels();

  dailyValues = [];
  weeklyValues = [5, 3, 6, 5, 1, 2, 4];
  monthlyValues = [5, 3, 6, 5, 1, 2, 4, 5, 3, 6, 5, 1, 2, 4, 5, 3, 6, 5, 1, 2, 4, 5, 3, 6, 5, 1, 2, 4, 2, 3, 5];

  constructor() {
  }

  ngOnInit() {
  }

  changeChart(type: ChartType) {
    this.currentChart = type;
  }

  isCurrentChart(type: ChartType) {
    return this.currentChart === type;
  }

  static getDailyChartLabels(): Array<string> {
    let datePipe = new DatePipe("en");
    let dates = [];
    for (let i = 11; i >= 0; i--) {
      let date = new Date();
      date.setHours(date.getHours() - i);
      dates.push(datePipe.transform(date, 'short'));
    }

    return dates;
  }

  static getWeeklyChartLabels(): Array<string> {
    let datePipe = new DatePipe("en");
    let dates = [];
    for (let i = 6; i >= 0; i--) {
      let date = new Date();
      date.setDate(date.getDate() - i);
      dates.push(datePipe.transform(date, 'mediumDate'));
    }

    return dates;
  }

  static getMonthlyChartLabels(): Array<string> {
    let datePipe = new DatePipe("en");
    let dates = [];
    for (let i = 29; i >= 0; i--) {
      let date = new Date();
      date.setDate(date.getDate() - i);
      dates.push(datePipe.transform(date, 'mediumDate'));
    }

    return dates;
  }
}

enum ChartType {
  Daily = 0, Weekly = 1, Monthly = 2
}
