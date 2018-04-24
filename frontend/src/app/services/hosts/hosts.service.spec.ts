import { TestBed, inject } from '@angular/core/testing';

import { HostsService } from './hosts.service';

describe('HostsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HostsService]
    });
  });

  it('should be created', inject([HostsService], (service: HostsService) => {
    expect(service).toBeTruthy();
  }));
});
