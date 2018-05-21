import {Component, Input, OnInit} from '@angular/core';
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
  peersIps: string = '';

  isDataAvailable: boolean = false;

  timestamps: Array<any> = [];
  allValues: Array<any> = [];

  inTimestamps: Array<any> = [];
  inValues: Array<any> = [];

  outTimestamps: Array<any> = [];
  outValues: Array<any> = [];
  params = StatsService.getDefaultQueryParams(StatsType.Daily);

  currentActivityType: TrafficType = TrafficType.All;

  constructor(private statsService: StatsService,
              private hostsService: HostsService,
              private modalService: NgbModal,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      const hostIp = params.ip;
      if (params.peerIp) {
        this.peersIps = params.peerIp;
      }
      this.hostsService.getHosts(hostIp).subscribe(hosts => {
        this.host = hosts.filter(host => host.ip == hostIp)[0];
        this.getStats(this.params.start, this.params.end, this.params.granularity);
      });
    });
  }

  getStats(begin: Date, end: Date, granularity: number) {
    let params = new StatsQueryParams(begin, end, granularity);
    const peersIps: Array<string> = this.getPeersIps()
    if (peersIps.length > 0) {
      params.peersIps = peersIps;
    }
    params.hostIp = this.host.ip;
    this.statsService.getStats(params)
      .subscribe(stats => {
        this.allValues = stats.getValues();
        this.timestamps = stats.getTimestamps();
        this.isDataAvailable = true;
      });

    params.trafficType = TrafficType.In;
    this.statsService.getStats(params)
      .subscribe(stats => {
        this.inValues = stats.getValues();
        this.inTimestamps = stats.getTimestamps();
      });

    params.trafficType = TrafficType.Out;
    this.statsService.getStats(params)
      .subscribe(stats => {
        this.outValues = stats.getValues();
        this.outTimestamps = stats.getTimestamps();
      });
  }

  private getPeersIps(): Array<string> {
    if (this.peersIps == '') {
      return [];
    }
    return this.peersIps.split(',');
  }

  changeActivityType(type: TrafficType) {
    this.currentActivityType = type;
  }

  open() {
    const modalRef = this.modalService.open(DateInputModalComponent);
    modalRef.componentInstance.notify.subscribe((parametersFromModal) => {
      this.params.start = parametersFromModal.begin
      this.params.end = parametersFromModal.end
      this.params.granularity = parametersFromModal.granularity
      this.getStats(this.params.start, this.params.end, this.params.granularity);
    });
  }

  recalculate() {
    this.getStats(this.params.start, this.params.end, this.params.granularity);
  }
}
