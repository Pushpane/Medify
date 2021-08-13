import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ICart } from './cart';
import { ICartPayload } from './cartPayload';
import { DashboardService } from './dashboard.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {

  medToStore: ICartPayload[];
  cart: ICartPayload[];
  constructor(private formBuilder: FormBuilder, private router: Router,
    private dashboardService: DashboardService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.fetch();
  }

  fetch() {
    this.dashboardService.getAllMedToStore().subscribe({
      next: data => {
        this.medToStore = data.map(x => {
          return {
            id: x.medicineToStoreId,
            name: x.medicineId.name,
            description: x.medicineId.description,
            price: x.medicineId.price,
            storeName: x.storeId.name,
            image: String(x.medicineId.image),
            available: x.available ? 'Available' : 'Not Available',
            cart: false
          }
        });
        this.fetchCart();
      },
      error: err => console.log(err)
    });
  }

  fetchCart() {
    if (localStorage.getItem("role") != null) {
      this.dashboardService.getCartByUser().subscribe({
        next: data => {
          console.log(data); this.cart = data.map(x => {
            return {
              id: x.medicineToStoreId.medicineToStoreId,
              name: x.medicineToStoreId.medicineId.name,
              description: x.medicineToStoreId.medicineId.description,
              price: x.medicineToStoreId.medicineId.price,
              storeName: x.medicineToStoreId.storeId.name,
              image: String(x.medicineToStoreId.medicineId.image),
              available: x.medicineToStoreId.available ? 'Available' : 'Not Available',
              cart: true
            }
          });
          this.filterData();
        },
        error: err => console.log(err)
      });
    }
  }

  filterData() {
    const data = this.cart.map(x => x.id);
    this.medToStore = this.medToStore.map(x => {
      if(data.includes(x.id)){
        x.cart = true
      }
      return x;
    });
  }

  addToCart(id: any) {
    if (localStorage.getItem("role") != null) {
      console.log(id);
      this.dashboardService.addToCart(id).subscribe({
        next: data => { console.log(data); this.addCart(id); },
        error: err => this.toastr.error('Cart Entry Failed! Please try again')
      });
    } else {
      this.toastr.error('Please Login for adding medicine to cart!');
      this.router.navigate(['/Login']);
    }
  }
  removeFromCart(id: any) {
    if (localStorage.getItem("role") != null) {
      this.dashboardService.deleteCart(id).subscribe({
        next: data => { console.log(data); this.removeCart(id) },
        error: err => this.toastr.error('Cart Removal Failed! Please try again')
      });
    } else {
      this.toastr.error('Please Login for any operations on cart!');
    }
  }
  removeCart(id:any){
    this.medToStore = this.medToStore.map(x=> {
      if(x.id===id){
        x.cart = false;
      }
      return x;
    });
  }
  addCart(id: any){
    this.medToStore = this.medToStore.map(x=>{
      if(x.id === id){
        x.cart= true;
      }
      return x;
    });
  }
}
