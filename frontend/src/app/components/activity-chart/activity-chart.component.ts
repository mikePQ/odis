import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-activity-chart',
  templateUrl: './activity-chart.component.html',
  styleUrls: ['./activity-chart.component.css']
})
export class ActivityChartComponent implements OnInit {

  @Input('values')
  values: Array<any>;

  @Input('labels')
  timestamps: Array<any>;

  constructor() {
  }

  ngOnInit() {
  }

}
