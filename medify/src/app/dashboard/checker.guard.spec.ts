import { TestBed } from '@angular/core/testing';

import { CheckerGuard } from './checker.guard';

describe('CheckerGuard', () => {
  let guard: CheckerGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CheckerGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
