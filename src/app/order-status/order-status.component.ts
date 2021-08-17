import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

class orderTracker {
  id: number;
  status: string;
}

@Component({
  selector: 'app-order-status',
  templateUrl: './order-status.component.html',
  styleUrls: ['./order-status.component.css']
})
export class OrderStatusComponent implements OnInit {



  constructor(@Inject(MAT_DIALOG_DATA) public data: orderTracker) {
  }

  ngOnInit(): void {
  }
  orderId: number = this.data.id;

  //change the key according to the value int database for order_status
  orderStatusMap: { [key: string]: number } = {
    "Order Placed": 1,
    "Order accepted": 2,
    "Packed": 3,
    "Delivered": 4,
  }
  // assign the value from database to order status using key value pair
  // assigning  the value will change the status in web page
  public orderStatus = this.orderStatusMap[this.data.status];


}
