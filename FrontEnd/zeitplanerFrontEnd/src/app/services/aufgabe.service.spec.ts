import { TestBed } from '@angular/core/testing';

import { AufgabeService } from './aufgabe.service';

describe('AufgabeService', () => {
  let service: AufgabeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AufgabeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
