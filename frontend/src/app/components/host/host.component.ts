import {Component, OnInit} from '@angular/core';
import {ActivitiesService} from '../../services/activities/activities.service';
import {Host} from '../../domain/host';
import {HostActivity} from '../../domain/host.activity';
import {HostsService} from '../../services/hosts/hosts.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-host',
  templateUrl: './host.component.html',
  styleUrls: ['./host.component.css']
})
export class HostComponent implements OnInit {

  host: Host;
  activity: HostActivity;

  public barChartOptions:any = {
    scaleShowVerticalLines: false,
    responsive: true
  };

  public barChartLabels:string[] = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
  public barChartType:string = 'bar';
  public barChartLegend:boolean = true;

  public barChartData:any[] = [
    {data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A'},
    {data: [28, 48, 40, 19, 86, 27, 90], label: 'Series B'}
  ];

  constructor(private activitiesService: ActivitiesService,
              private hostsService: HostsService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let hostIp = params.ip;
      this.hostsService.getHosts(hostIp).subscribe(hosts => {
        this.host = hosts[0];
        this.activitiesService.getHostActivity(this.host).subscribe(activity => {
          this.activity = activity;
        });
      });
    });
  }

  rangeChanged(event: any) {

  }
}
