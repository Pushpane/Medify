import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { IAddress } from './address';
import { DashboardService } from './dashboard.service';
import { IStore } from './store';

@Component({
  selector: 'app-add-store',
  templateUrl: './add-store.component.html',
  styleUrls: ['./add-store.component.css']
})
export class AddStoreComponent implements OnInit {
  
  displayedColumns: string[] = ['name', 'description', 'address1', 'address2','city','state','pincode'];
  storeForm: FormGroup;
  stores: IStore[];
  address: any;
  dataSource: MatTableDataSource<IAddress>;

  @ViewChild(MatSort) sort: MatSort;

  constructor(private formBuilder: FormBuilder,private router: Router,
    private dashboardService: DashboardService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    if(localStorage.getItem("role") !=null){
      this.fetch();
    }
    this.storeForm = this.formBuilder.group({
      name: ['',[Validators.required, Validators.minLength(5)]],
      description: ['',[Validators.required, Validators.minLength(10)]],
      address1: ['',[Validators.required, Validators.minLength(10)]],
      address2: [''],
      city: ['',[Validators.required, Validators.minLength(3)]],
      state: ['',[Validators.required, Validators.minLength(3)]],
      pincode: ['',[Validators.required, Validators.minLength(3)]]
    });
  }

  fetch(){
    this.dashboardService.getAllAddressByUser().subscribe({
      next: data=> {
        this.address=data.map(x=>{return {
          name: x.storeId.name,
          description: x.storeId.description,
          address1: x.address1,
          address2: x.address2,
          city: x.city,
          state: x.state,
          pincode: x.pincode}
        });
        console.log(data); 
        this.loadTable();
      },
      error: err=> console.log(err)
    });
  }

  loadTable(){
    this.dataSource = new MatTableDataSource<IAddress>(this.address);
    
    this.dataSource.sort = this.sort;
  }
  addStore(): void {
    if(this.storeForm.valid){
      const store = {
        id: 0, 
        name: this.storeForm.get('name').value, 
        description: this.storeForm.get('description').value
      };
      let address = {
        address1: this.storeForm.get('address1').value,
        address2: this.storeForm.get('address2').value,
        pincode: this.storeForm.get('pincode').value,
        city: this.storeForm.get('city').value,
        state: this.storeForm.get('state').value
      };
      this.dashboardService.addStore(store)
        .subscribe({
          next: data=> {console.log(data);this.onSaveComplete(address,data);},
          error: err=> this.toastr.error('Store entry Failed! Please try again')
        });
    }
    else{
      this.toastr.error('Please correct the Validation errors');
    }
  }

  onSaveComplete(address: any,store: any){
    const data = {...address, storeId: store.storeId};
    this.dashboardService.addAddress(data)
        .subscribe({
          next: data=> {this.onComplete(); console.log(data)},
          error: err=> this.toastr.error('Address entry Failed! Please try again')
        });
  }

  onComplete(){
    this.storeForm.reset();
    window.location.reload();
  }
}
