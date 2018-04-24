import {Component, Input, OnInit} from '@angular/core';
import {ActivitiesService} from '../../services/activities/activities.service';
import {Host} from '../../domain/host';
import {HostActivity} from '../../domain/host.activity';

@Component({
  selector: 'app-host',
  templateUrl: './host.component.html',
  styleUrls: ['./host.component.css']
})
export class HostComponent implements OnInit {

  @Input('host')
  host: Host;

  activity: HostActivity;

  constructor(private activitiesService: ActivitiesService) {
  }

  ngOnInit() {
    this.activity = this.activitiesService.getHostActivity(this.host);
  }

}
