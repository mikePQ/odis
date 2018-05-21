import {Component, EventEmitter, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Host} from '../../domain/host';
import {HostsService} from '../../services/hosts/hosts.service';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-host-input',
  templateUrl: './host-input.component.html',
  styleUrls: ['./host-input.component.css']
})
export class HostInputComponent implements OnInit {

  constructor(private hostsService: HostsService, public activeModal: NgbActiveModal) { }

  host: Host = new Host();
  hosts: Array<Host> = [];
  allHosts: Host = {'name': 'Wszyscy uzytkownicy', 'ip': ''};

  @Output('HostChoose')
  notify: EventEmitter<Host> = new EventEmitter<Host>();
  @Output('HostRemove')
  notifyRemove: EventEmitter<Host> = new EventEmitter<Host>();
  newUserAddition = false;

  ngOnInit() {
    this.getLocalHosts();
  }

  private getLocalHosts() {
    this.hostsService.getLocalHosts().subscribe((localHosts) => this.hosts = localHosts);
  }

  addHost() {
    this.hostsService.postLocalHosts(this.host).subscribe((host) => {
      console.log(host);
      this.getLocalHosts();
    });
    this.newUserAddition = false;
  }

  returnHost(host: Host) {
    console.log(host)
    this.notify.emit(host);
    this.activeModal.dismiss('User returned');
  }

  removeHost(host: Host) {
    this.notifyRemove.emit(host);
    this.hosts = this.hosts.filter((hostElem) => hostElem.ip != host.ip);
  }


  goToUserAddition() {
    this.newUserAddition = true;
  }
}
