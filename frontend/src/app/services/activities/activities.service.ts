import {Injectable} from '@angular/core';
import {Host} from '../../domain/host';
import {Observable} from 'rxjs/Observable';
import {HostActivity} from '../../domain/host.activity';

@Injectable()
export class ActivitiesService {

  constructor() { }

  getHostActivity(host: Host): Observable<HostActivity> {
    return undefined;
  }
}
