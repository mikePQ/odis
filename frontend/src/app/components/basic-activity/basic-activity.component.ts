import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {StatsService} from '../../services/stats/stats.service';
import {Stats, StatsQueryParams, StatsType} from '../../domain/stats';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DateInputModalComponent} from '../date-input-modal/date-input-modal.component';

@Component({
  selector: 'app-basic-activity',
  templateUrl: './basic-activity.component.html',
  styleUrls: ['./basic-activity.component.css']
})
export class BasicActivityComponent implements OnInit {

  @Output('dailyStats')
  dailyStatsEventEmitter: EventEmitter<Stats> = new EventEmitter<Stats>();

  @Output('weeklyStats')
  weeklyStatsEventEmitter: EventEmitter<Stats> = new EventEmitter<Stats>();

  @Output('monthlyStats')
  monthlyStatsEventEmitter: EventEmitter<Stats> = new EventEmitter<Stats>();

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

  getStats(begin: Date, end: Date, granularity: number) {
    let params = new StatsQueryParams(begin, end, granularity);
    this.statsService.getStats(params)
      .subscribe(stats => {
        this.detailedChartLabels = stats.getTimestamps();
        this.detailedValues = stats.getValues();
        this.showDetailedChart = true;
      });
  }

  isCurrentChart(type: ChartType) {
    return this.currentChart === type;
  }

  getDailyValues(): void {
    let params = StatsService.getDefaultQueryParams(StatsType.Daily);
    this.statsService.getStats(params)
      .subscribe(stats => {
        this.dailyChartLabels = stats.getTimestamps();
        this.dailyValues = stats.getValues();
        this.dailyStatsEventEmitter.emit(stats);
      });
  }

  getWeeklyValues() {
    let params = StatsService.getDefaultQueryParams(StatsType.Weekly);
    this.statsService.getStats(params)
      .subscribe(stats => {
        this.weeklyChartLabels = stats.getTimestamps();
        this.weeklyValues = stats.getValues();
        this.weeklyStatsEventEmitter.emit(stats);
      });
  }

  getMonthlyValues() {
    let params = StatsService.getDefaultQueryParams(StatsType.Monthly);
    this.statsService.getStats(params)
      .subscribe(stats => {
        this.monthlyChartLabels = stats.getTimestamps();
        this.monthlyValues = stats.getValues();
        this.monthlyStatsEventEmitter.emit(stats);
      });
  }

  open() {
    const modalRef = this.modalService.open(DateInputModalComponent);
    modalRef.componentInstance.notify.subscribe((params) => {
      this.getStats(params.begin, params.end, params.granularity);
    });
  }
}

enum ChartType {
  Daily = 0, Weekly = 1, Monthly = 2
}
