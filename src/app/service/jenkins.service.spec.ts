import { TestBed } from '@angular/core/testing';

import { JenkinsService } from './flights.service';

describe('JenkinsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: JenkinsService = TestBed.get(JenkinsService);
    expect(service).toBeTruthy();
  });
});
