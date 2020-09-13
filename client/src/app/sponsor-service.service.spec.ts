import { TestBed } from '@angular/core/testing';

import { SponsorServiceService } from './sponsor-service.service';

describe('SponsorServiceService', () => {
  let service: SponsorServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SponsorServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
