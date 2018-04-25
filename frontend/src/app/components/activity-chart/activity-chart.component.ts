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

  colors = [{
    backgroundColor: 'rgba(151,187,205,0.2)',
    borderColor: 'rgba(151,187,205,1)',
    pointBackgroundColor: 'rgba(151,187,205,1)',
    pointBorderColor: '#fff',
    pointHoverBackgroundColor: '#fff',
    pointHoverBorderColor: 'rgba(151,187,205,0.8)'
  }];

  constructor() {
  }

  ngOnInit() {
  }

}
