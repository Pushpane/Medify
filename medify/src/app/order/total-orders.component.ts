import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DashboardService } from '../dashboard/dashboard.service';
import { IOrderStatus } from './order-status';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {OrderStatusComponent} from "../order-status/order-status.component";
import {OrderStatusCancelComponent} from "../order-status/order-status-cancel.component";

@Component({
  selector: 'app-total-orders',
  templateUrl: './total-orders.component.html',
  styleUrls: ['./total-orders.component.css']
})
export class TotalOrdersComponent implements OnInit {

  displayedColumns: string[] = ['name', 'description', 'price', 'storeName','quantity','cost', 'status', 'address'];
  order: IOrderStatus[];
  dataSource: MatTableDataSource<any>;


  @ViewChild(MatSort) sort: MatSort;

  constructor(private formBuilder: FormBuilder, private router: Router,
    private dashboardService: DashboardService,
    private toastr: ToastrService, private orderStatusDialog: MatDialog ) { }

  ngOnInit(): void {
    this.fetch();
  }

  fetch(){
    this.dashboardService.getAllOrders().subscribe({
      next: data=> {console.log(data); this.order = data.map(x=> {
        return {
          id: x.orderId,
          name: x.medicineToStoreId.medicineId.name,
          description: x.medicineToStoreId.medicineId.description,
          price: Number(x.medicineToStoreId.medicineId.price),
          storeName: x.medicineToStoreId.storeId.name,
          address: x.addressId.address1.concat(" "+x.addressId.address2+x.addressId.city+x.addressId.state+x.addressId.pincode),
          status: x.orderStatus,
          cost: x.cost,
          quantity: x.quantity
        }
      });
      console.log(this.order);
      this.loadTable();
    },
      error: err=> console.log(err)
    });
  }

  loadTable(){
    this.dataSource = new MatTableDataSource<any>(this.order);

    this.dataSource.sort = this.sort;
  }

  trackOrder(id: number, status: string) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "1080px"
    dialogConfig.data = {
      id: id,
      status: status
    };
    if (status == "Cancelled"){
      this.orderStatusDialog.open(OrderStatusCancelComponent, dialogConfig)
    }else {
      this.orderStatusDialog.open(OrderStatusComponent, dialogConfig)
    }

  }
}
