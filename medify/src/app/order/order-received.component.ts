import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DashboardService } from '../dashboard/dashboard.service';
import { IMarkPacked } from './markPacked';

@Component({
  selector: 'app-order-received',
  templateUrl: './order-received.component.html',
  styleUrls: ['./order-received.component.css']
})
export class OrderReceivedComponent implements OnInit {
  
  displayedColumns: string[] = ['id', 'userName', 'email', 'medicine','quantity','cost', 'storeName', 'status', 'decline'];
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
      next: data=> {console.log(data);this.order = data.filter(x=> x.orderStatus==='Order Placed').map(x=> {
        return {
          id: x.orderId,
          userName: x.userId.name,
          email: x.userId.email,
          medicine: x.medicineToStoreId.medicineId.name,
          quantity: x.quantity,
          cost: x.cost,
          storeName: x.medicineToStoreId.storeId.name
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

  markPacked(id: any){
    this.dashboardService.markPacked(id).subscribe({
      next: data=> {console.log(data); this.order = this.order.filter(x=> x.id !== id); this.loadTable();},
      error: err => this.toastr.error("Status not changed! Try again.") 
    });
  }
  
  declineOrder(id: any){
    this.dashboardService.declineOrder(id).subscribe({
      next: data=> {console.log(data);this.order = this.order.filter(x=> x.id !== id); this.loadTable();},
      error: err => this.toastr.error("Status not changed! Try again.") 
    });
  }
}
