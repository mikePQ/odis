import {Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {BaseChartDirective} from 'ng2-charts';

@Component({
  selector: 'app-activity-chart',
  templateUrl: './activity-chart.component.html',
  styleUrls: ['./activity-chart.component.css']
})
export class ActivityChartComponent implements OnInit, OnChanges {

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

  @ViewChild('baseChart') chart: BaseChartDirective;

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

  reloadChart() {
    if (this.chart !== undefined && this.chart.chart !== undefined) {
      this.chart.chart.destroy();
      this.chart.chart = 0;

      this.chart.datasets = this.labeledValues;
      this.chart.labels = this.timestamps;
      this.chart.ngOnInit();
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log("OnChanges")
    console.log(this.values)
    this.labeledValues = [{data: this.values, label: this.name}];
    this.reloadChart()
  }
}
