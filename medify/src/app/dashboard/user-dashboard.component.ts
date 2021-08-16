import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { IAddress } from './address';
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
  filteredMedToStore: ICartPayload[];
  filteredCart: ICartPayload[];
  state: any[]=[];
  city: any[]=[];

  address: IAddress[];

  private _listFilter: string = '';
  private _stateFilter: string = '';
  private _cityFilter: string = '';
  
  get stateFilter(): string {
    return this._stateFilter;
  }
  set stateFilter(value: string){
    this._stateFilter = value;
    console.log('In setter: ', value);
    this.city = Array.from(new Set(this.address.filter(x=> x.state === value).map(x=>{
      return x.city;
    })));
    this.performFilter();
  }

  get cityFilter(): string {
    return this._cityFilter;
  }
  set cityFilter(value: string){
    this._cityFilter = value;
    console.log('In setter: ', value);
    this.performFilter();
  }

  get listFilter(): string {
      return this._listFilter;
  }
  set listFilter(value: string) {
      this._listFilter = value;
      console.log('In setter: ', value);
      this.performFilter();
  }


  performFilter(){
    this.filteredMedToStore = this.medToStore.filter(x=> x.name.toLowerCase().includes(this.listFilter.toLowerCase()) && 
    x.address.state.includes(this.stateFilter) && x.address.city.includes(this.cityFilter));
    this.filteredCart = this.cart.filter(x=> x.name.includes(this.listFilter) && 
    x.address.state.includes(this.stateFilter) && x.address.city.includes(this.cityFilter));
  }

  constructor(private formBuilder: FormBuilder, private router: Router,
    private dashboardService: DashboardService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.fetch();
  }

  fetch() {
    this.dashboardService.getAllAddress().subscribe({
      next: data => {
        this.address = data;
        this.state = Array.from(new Set(data.map(x=>{
          return x.state;
        })));
        console.log(this.state);
        this.fetchAgain();
      },
      error: err=> console.log(err)
    });
  }
  fetchAgain(){
    this.dashboardService.getAllMedToStore().subscribe({
      next: data => {
        this.medToStore = data.map(x => {
          let address= this.address.find(y=> y.storeId.storeId === x.storeId.storeId);
          return {
            id: x.medicineToStoreId,
            name: x.medicineId.name,
            description: x.medicineId.description,
            price: x.medicineId.price,
            storeName: x.storeId.name,
            image: String(x.medicineId.image),
            available: x.available ? 'Available' : 'Not Available',
            cart: false,
            address: address
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
            let address = this.address.find(y=> y.storeId.storeId === x.medicineToStoreId.storeId.storeId);
            return {
              id: x.medicineToStoreId.medicineToStoreId,
              name: x.medicineToStoreId.medicineId.name,
              description: x.medicineToStoreId.medicineId.description,
              price: x.medicineToStoreId.medicineId.price,
              storeName: x.medicineToStoreId.storeId.name,
              image: String(x.medicineToStoreId.medicineId.image),
              available: x.medicineToStoreId.available ? 'Available' : 'Not Available',
              cart: true,
              address: address
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
    this.performFilter();
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
