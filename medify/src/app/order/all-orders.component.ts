import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DashboardService } from '../dashboard/dashboard.service';
import { IMarkPacked } from './markPacked';

@Component({
  selector: 'app-all-orders',
  templateUrl: './all-orders.component.html',
  styleUrls: ['./all-orders.component.css']
})
export class AllOrdersComponent implements OnInit {
  displayedColumns: string[] = ['id', 'userName', 'email', 'medicine','quantity','cost', 'storeName', 'status'];
  order: IMarkPacked[];
  dataSource: MatTableDataSource<any>;
  
  @ViewChild(MatSort) sort: MatSort;

  constructor(private formBuilder: FormBuilder, private router: Router,
    private dashboardService: DashboardService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.fetch();
  }

  fetch(){
    this.dashboardService.getOrderReceived().subscribe({
      next: data=> {console.log(data);this.order = data.map(x=> {
        return {
          id: x.orderId,
          userName: x.userId.name,
          email: x.userId.email,
          medicine: x.medicineToStoreId.medicineId.name,
          quantity: x.quantity,
          cost: x.cost,
          storeName: x.medicineToStoreId.storeId.name,
          status: x.orderStatus
        }
      });
      console.log(this.order);
      this.loadTable();
    },
      error: err => console.log(err)
    });
  }

  loadTable(){
    this.dataSource = new MatTableDataSource<any>(this.order);
    
    this.dataSource.sort = this.sort;
  }

}
