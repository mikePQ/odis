import {Injectable} from '@angular/core';
import {Host} from '../../domain/host';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {api} from '../../api';
import {ArrayObservable} from 'rxjs/observable/ArrayObservable';

@Injectable()
export class HostsService {

  private hostsApi: string = api.getHosts();

  constructor(private httpClient: HttpClient) {
  }

  getHosts(): Observable<Array<Host>> {
    return ArrayObservable.of([{name: 'Janusz', ip: '123.12324'}]);
    //return this.httpClient.get<Host[]>(this.hostsApi);
  }

  getHost(hostId: number): Observable<Host> {
    return undefined;
  }
}
