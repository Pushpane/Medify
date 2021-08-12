import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-order-status',
  templateUrl: './order-status.component.html',
  styleUrls: ['./order-status.component.css']
})
export class OrderStatusComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }
  orderId: number = 103044;

  //change the key according to the value int database for order_status
  orderStatusMap: { [key: string]: number } = {
    "Order Placed": 1,
    "Order accepted": 2,
    "Packed": 3,
    "Picked": 4,
  }
  // assign the value from database to order status using key value pair
  // assigning  the value will change the status in web page
  public orderStatus = this.orderStatusMap["Order Placed"];


}
