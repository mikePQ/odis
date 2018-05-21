import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Host} from '../../../domain/host';

@Component({
  selector: 'app-host-table-element',
  templateUrl: './host-table-element.component.html',
  styleUrls: ['./host-table-element.component.css']
})
export class HostTableElementComponent implements OnInit {

  constructor() {
  }

  @Input('Host')
  host: Host;

  @Output('HostClicked')
  hostEmitter: EventEmitter<Host> = new EventEmitter<Host>();
  @Output('HostRemoved')
  hostRemovedEmitter: EventEmitter<Host> = new EventEmitter<Host>();

  ngOnInit() {
  }

  returnHost() {
    this.hostEmitter.emit(this.host);
  }


  removeHost() {
    this.hostRemovedEmitter.emit(this.host);
  }
}
