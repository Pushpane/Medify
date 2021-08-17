import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

class orderTracker {
  id: number;
}
@Component({
  selector: 'app-order-status-cancel',
  templateUrl: './order-status-cancel.component.html',
  styleUrls: ['./order-status-cancel.component.css']
})
export class OrderStatusCancelComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: orderTracker) { }

  ngOnInit(): void {
  }
  orderId: number = this.data.id;
  public orderStatus = "Order Cancel";
}
