import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-order-status-cancel',
  templateUrl: './order-status-cancel.component.html',
  styleUrls: ['./order-status-cancel.component.css']
})
export class OrderStatusCancelComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  orderId: number = 103044;
  public orderStatus = "Order Cancel";
}
