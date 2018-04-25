import {Component, OnInit} from '@angular/core';
import {Host} from '../../domain/host';
import {HostsService} from '../../services/hosts/hosts.service';

@Component({
  selector: 'app-hosts',
  templateUrl: './hosts.component.html',
  styleUrls: ['./hosts.component.css']
})
export class HostsComponent implements OnInit {

  hosts: Array<Host> = [];

  constructor(private hostsService: HostsService) {
  }

  ngOnInit() {
    this.hostsService.getHosts().subscribe(hosts => {
      this.hosts = hosts;
    });
  }

}
