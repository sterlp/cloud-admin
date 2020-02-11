import { TestBed } from '@angular/core/testing';

import { SpringResoureceService } from './spring-resourece.service';

describe('SpringResoureceService', () => {
  let service: SpringResoureceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpringResoureceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
