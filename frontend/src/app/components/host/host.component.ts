import {Component, OnInit} from '@angular/core';
import {Host} from '../../domain/host';
import {HostsService} from '../../services/hosts/hosts.service';
import {ActivatedRoute} from '@angular/router';
import {StatsService} from '../../services/stats/stats.service';
import {StatsQueryParams, StatsType, TrafficType} from '../../domain/stats';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DateInputModalComponent} from '../date-input-modal/date-input-modal.component';

@Component({
  selector: 'app-host',
  templateUrl: './host.component.html',
  styleUrls: ['./host.component.css']
})
export class HostComponent implements OnInit {
  activityType = TrafficType;
  host: Host;

  isDataAvailable: boolean = false;

  timestamps: Array<any> = [];
  allValues: Array<any> = [];

  inTimestamps: Array<any> = [];
  inValues: Array<any> = [];

  outTimestamps: Array<any> = [];
  outValues: Array<any> = [];

  currentActivityType: TrafficType = TrafficType.All;

  constructor(private statsService: StatsService,
              private hostsService: HostsService,
              private modalService: NgbModal,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let hostIp = params.ip;
      this.hostsService.getHosts(hostIp).subscribe(hosts => {
        this.host = hosts[0];
        let params = StatsService.getDefaultQueryParams(StatsType.Daily);
        this.getStats(params.start, params.end, params.granularity);
      });
    });
  }

  getStats(begin: Date, end: Date, granularity: number) {
    let params = new StatsQueryParams(begin, end, granularity);
    params.hostIp = this.host.ip;
    this.statsService.getStats(params)
      .subscribe(stats => {
        this.timestamps = stats.getTimestamps();
        this.allValues = stats.getValues();
        this.isDataAvailable = true;
      });

    params.trafficType = TrafficType.In;
    this.statsService.getStats(params)
      .subscribe(stats => {
        this.inTimestamps = stats.getTimestamps();
        this.inValues = stats.getValues();
      });

    params.trafficType = TrafficType.Out;
    this.statsService.getStats(params)
      .subscribe(stats => {
        this.outTimestamps = stats.getTimestamps();
        this.outValues = stats.getValues();
      });
  }

  changeActivityType(type: TrafficType) {
    this.currentActivityType = type;
  }

  open() {
    const modalRef = this.modalService.open(DateInputModalComponent);
    modalRef.componentInstance.notify.subscribe((params) => {
      this.getStats(params.begin, params.end, params.granularity);
    });
  }
}
