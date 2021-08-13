import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ICartPayload } from './cartPayload';
import { DashboardService } from './dashboard.service';
import { IOrderCart } from './order-cart';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  displayedColumns: string[] = ['name', 'description', 'price', 'storeName','quantity','cost'];
  cart: IOrderCart[];
  dataSource: MatTableDataSource<any>;
  
  @ViewChild(MatSort) sort: MatSort;

  constructor(private formBuilder: FormBuilder, private router: Router,
    private dashboardService: DashboardService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.fetch();
  }

  fetch(){
    this.dashboardService.getCartByUser().subscribe({
      next: data => {
        console.log(data); this.cart = data.map(x => {
          return {
            id: x.cartId,
            name: x.medicineToStoreId.medicineId.name,
            description: x.medicineToStoreId.medicineId.description,
            price: x.medicineToStoreId.medicineId.price,
            storeName: x.medicineToStoreId.storeId.name,
            image: String(x.medicineToStoreId.medicineId.image),
            available: x.medicineToStoreId.available ? 'Available' : 'Not Available',
            cart: true,
            quantity: x.quantity,
            cost: x.cost
          }
        });
        console.log(this.cart);
        this.loadTable();
      },
      error: err => console.log(err)
    });
  }

  loadTable(){
    this.dataSource = new MatTableDataSource<any>(this.cart);
    
    this.dataSource.sort = this.sort;
  }

  addQuantity(id: any){
    this.dashboardService.addQuantity(id).subscribe({
      next: data => {console.log(data); this.cart = this.cart.map(x=>{
        if(x.id===id){
          x.quantity = x.quantity + 1;
          x.cost = x.quantity * Number(x.price);
        }
        return x;
      })},
      error: err => console.log(err)
    });   
  }

  removeQuantity(id: any){
    if(this.cart.find(x=> x.id === id).quantity !== 1){
      this.dashboardService.removeQuantity(id).subscribe({
        next: data => {console.log(data); this.cart = this.cart.map(x=>{
          if(x.id===id){
            x.quantity = x.quantity - 1;
            x.cost = x.quantity * Number(x.price);
          }
          return x;
        })},
        error: err => console.log(err)
      });
    }else{
      this.toastr.error("Cart Item quantity can't be less than 1!");
    }
  }
}
