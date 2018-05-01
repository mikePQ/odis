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

  @Input('name')
  name: string;

  colors: Array<any> = [{
    backgroundColor: '#3e95cd',
    borderColor: '#3e95cd',
    pointBackgroundColor: '#3e95cd',
    pointBorderColor: '#fff',
    pointHoverBackgroundColor: '#fff',
    pointHoverBorderColor: 'rgba(77,83,96,1)'
  }];

  labeledValues: Array<any> = [];

  constructor() {
  }

  ngOnInit() {
    this.labeledValues = [{data: this.values, label: this.name}];
  }

}
