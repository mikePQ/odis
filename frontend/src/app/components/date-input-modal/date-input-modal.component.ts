import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-date-input-modal',
  templateUrl: './date-input-modal.component.html',
  styleUrls: ['./date-input-modal.component.css']
})
export class DateInputModalComponent implements OnInit {

  @Output()
  notify: EventEmitter<any> = new EventEmitter<any>();

  begin: Date = new Date();
  end: Date = new Date();
  granularity: number = 10;

  constructor(public activeModal: NgbActiveModal) {
  }

  ngOnInit() {
  }

  returnParameters() {
    this.notify.emit({begin: this.begin, end: this.end, granularity: this.granularity});
    this.activeModal.dismiss('Parameters returned');
  }

  updateBeginDate(event: string) {
    this.begin = new Date(event);
  }

  updateEndDate(event: string) {
    this.end = new Date(event);
  }

}
