import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Pager} from '../../services/pagination.service';

@Component({
  selector: 'app-pages-nav',
  templateUrl: './pages-nav.component.html',
  styleUrls: ['./pages-nav.component.css']
})
export class PagesNavComponent implements OnInit {

  @Input('pager')
  pager: Pager;

  @Output()
  changePageEventEmitter: EventEmitter<any> = new EventEmitter();


  constructor() {
  }

  ngOnInit() {
  }

  changePage(page: number) {
    this.changePageEventEmitter.emit(page);
  }
}
