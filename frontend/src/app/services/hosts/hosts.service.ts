import {Injectable} from '@angular/core';
import {Host} from '../../domain/host';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {api} from '../../api';

@Injectable()
export class HostsService {

  private hostsApi: string = api.getHosts();
  private localHostsApi: string = api.getLocalHosts();

  constructor(private httpClient: HttpClient) {
  }

  getHosts(hostIp: string = ''): Observable<Array<Host>> {
    if (!hostIp) {
      return this.httpClient.get<Host[]>(this.hostsApi);
    }
    return this.httpClient.get<Host[]>(`${this.hostsApi}?ip=${hostIp}`);
  }

  getLocalHosts(): Observable<Array<Host>> {
    return this.httpClient.get<Host[]>(this.localHostsApi);
  }

  postLocalHosts(host: Host): Observable<Host> {
    return this.httpClient.post<Host>(this.localHostsApi, host);
  }

  removeHost(host: Host): Observable<Host>  {
    const url: string = `${this.localHostsApi}?hostIp=${host.ip}`;
    console.log(url)
    return this.httpClient.delete<Host>(url);
  }
}
