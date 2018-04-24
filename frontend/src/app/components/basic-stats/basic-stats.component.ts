import {Component, Input, OnInit} from '@angular/core';
import {Stats} from '../../domain/stats';

@Component({
  selector: 'app-basic-stats',
  templateUrl: './basic-stats.component.html',
  styleUrls: ['./basic-stats.component.css']
})
export class BasicStatsComponent implements OnInit {

  @Input("dailyStats")
  dailyStats: Stats;

  @Input("weeklyStats")
  weeklyStats: Stats;

  @Input("monthlyStats")
  monthlyStats: Stats;

  constructor() { }

  ngOnInit() {
  }

}
