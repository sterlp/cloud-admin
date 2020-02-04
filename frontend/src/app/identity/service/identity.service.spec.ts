import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IdentityService } from './identity.service';

describe('IdentityService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule]
  }));

  it('should be created', () => {
    const service: IdentityService = TestBed.get(IdentityService);
    expect(service).toBeTruthy();
  });
});
