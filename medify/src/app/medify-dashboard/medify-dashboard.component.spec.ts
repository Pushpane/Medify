import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedifyDashboardComponent } from './medify-dashboard.component';

describe('MedifyDashboardComponent', () => {
  let component: MedifyDashboardComponent;
  let fixture: ComponentFixture<MedifyDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedifyDashboardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MedifyDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
