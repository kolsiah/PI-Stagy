import { TestBed } from '@angular/core/testing';

import { ValdationService } from './valdation.service';

describe('ValdationService', () => {
  let service: ValdationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ValdationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
