import {Component, OnInit} from '@angular/core';
import {Stats} from '../../domain/stats';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  dailyStats: Stats;
  weeklyStats: Stats;
  monthlyStats: Stats;

  constructor() {
  }

  ngOnInit() {
  }

  updateDailyStats(stats: Stats) {
    this.dailyStats = stats;
  }

  updateWeeklyStats(stats: Stats) {
    this.weeklyStats = stats;
  }

  updateMonthlyStats(stats: Stats) {
    this.monthlyStats = stats;
  }
}
