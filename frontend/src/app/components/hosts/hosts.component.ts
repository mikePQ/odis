import {Component, Input, OnInit} from '@angular/core';
import {Host} from '../../domain/host';
import {HostsService} from '../../services/hosts/hosts.service';
import {Pager, PaginationService} from '../../services/pagination.service';

@Component({
  selector: 'app-hosts',
  templateUrl: './hosts.component.html',
  styleUrls: ['./hosts.component.css']
})
export class HostsComponent implements OnInit {

  hosts: Array<Host> = [];
  filteredHosts: Array<Host> = [];

  pager: Pager = Pager.empty();
  pagedHosts: Array<Host> = [];

  filters: Array<Filter> = [];

  @Input()
  name: string;

  @Input()
  ip: string;

  @Input()
  namePattern: string;

  @Input()
  ipPattern: string;

  constructor(private hostsService: HostsService,
              private paginationService: PaginationService) {
  }

  ngOnInit() {
    this.hostsService.getHosts().subscribe(hosts => {
      this.hosts = hosts;
      this.updateHosts();
    });
    this.setPage(1);
  }

  updateFilters() {
    let filters = [];
    if (this.namePattern && this.namePattern.length > 0) {
      let nameFilter = new NameFilter(this.namePattern);
      filters.push(nameFilter);
    }

    if (this.ipPattern && this.ipPattern.length > 0) {
      let ipFilter = new IpFilter(this.ipPattern);
      filters.push(ipFilter);
    }

    this.filters = filters;
    this.updateHosts();
  }

  removeFilter(filter: Filter) {
    this.filters = this.filters.filter(currentFilter => currentFilter.attribute != filter.attribute);
    this.updateHosts();
  }

  updateHosts() {
    this.filteredHosts = this.hosts.filter(host => {
      for (let filter of this.filters) {
        if (!filter.matches(host)) {
          return false;
        }
      }
      return true;
    });

    this.setPage(1);
  }

  setPage(page: number) {
    if (page < 1) {
      return;
    }

    this.pager = this.paginationService.getPager(this.filteredHosts.length, page);
    this.pagedHosts = this.filteredHosts.slice(this.pager.startIndex, this.pager.endIndex + 1);
  }
}

export interface Filter {
  attribute: string;
  pattern: string;

  matches(host: Host): boolean;
}

class NameFilter implements Filter {
  attribute: string = 'name';

  constructor(public pattern: string) {
  }

  matches(host: Host): boolean {
    return host.name.includes(this.pattern);
  }
}

class IpFilter implements Filter {
  attribute: string = 'ip';

  constructor(public pattern: string) {
  }

  matches(host: Host): boolean {
    return host.ip.includes(this.pattern);
  }
}
