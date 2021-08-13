import { TestBed } from '@angular/core/testing';

import { OwnerCheckerGuard } from './owner-checker.guard';

describe('OwnerCheckerGuard', () => {
  let guard: OwnerCheckerGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(OwnerCheckerGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
