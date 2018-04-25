import {Injectable} from '@angular/core';
import {Host} from '../../domain/host';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {api} from '../../api';

@Injectable()
export class HostsService {

  private hostsApi: string = api.getHosts();

  constructor(private httpClient: HttpClient) {
  }

  getHosts(): Observable<Array<Host>> {
    return this.httpClient.get<Host[]>(this.hostsApi);
  }

}
