import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderPackedComponent } from './order-packed.component';

describe('OrderPackedComponent', () => {
  let component: OrderPackedComponent;
  let fixture: ComponentFixture<OrderPackedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderPackedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderPackedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
