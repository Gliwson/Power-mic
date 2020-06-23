import { TestBed } from '@angular/core/testing';

import { PowerStationService } from './power-station.service';

describe('PowerStationService', () => {
  let service: PowerStationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PowerStationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
