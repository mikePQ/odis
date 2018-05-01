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

  @Input('color')
  color: string;

  colors: Array<any> = [];

  labeledValues: Array<any> = [];

  constructor() {
  }

  ngOnInit() {
    this.labeledValues = [{data: this.values, label: this.name}];
    if (!this.color) {
      this.color = '#3e95cd';
    }

    this.colors = [{
      backgroundColor: this.color,
      borderColor: this.color,
      pointBackgroundColor: this.color,
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    }];
  }

}
