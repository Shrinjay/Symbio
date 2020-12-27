import { TestBed } from '@angular/core/testing';

import { GlobalToggleService } from './global-toggle.service';

describe('GlobalToggleService', () => {
  let service: GlobalToggleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GlobalToggleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
