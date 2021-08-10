import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderStatusCancelComponent } from './order-status-cancel.component';

describe('OrderStatusCancelComponent', () => {
  let component: OrderStatusCancelComponent;
  let fixture: ComponentFixture<OrderStatusCancelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderStatusCancelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderStatusCancelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
