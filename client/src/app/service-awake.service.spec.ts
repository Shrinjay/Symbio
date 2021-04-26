import { TestBed } from '@angular/core/testing';

import { ServiceAwakeService } from './service-awake.service';

describe('ServiceAwakeService', () => {
  let service: ServiceAwakeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceAwakeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
