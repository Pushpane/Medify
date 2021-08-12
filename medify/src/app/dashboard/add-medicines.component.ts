import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { RxwebValidators } from '@rxweb/reactive-form-validators';
import { ToastrService } from 'ngx-toastr';
import { DashboardService } from './dashboard.service';
import { IMedicine } from './medicine';
import { IMedToStore } from './medToStore';
import { IStore } from './store';

@Component({
  selector: 'app-add-medicines',
  templateUrl: './add-medicines.component.html',
  styleUrls: ['./add-medicines.component.css']
})
export class AddMedicinesComponent implements OnInit {
  
  displayedColumns: string[] = ['name', 'description', 'price', 'storeName','available'];
  medicineForm: FormGroup;
  file: File;
  medicine: IMedicine;
  store: IStore[];
  medToStore: any;
  dataSource: MatTableDataSource<any>;

  @ViewChild(MatSort) sort: MatSort;

  constructor(private formBuilder: FormBuilder,private router: Router,
    private dashboardService: DashboardService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    if(localStorage.getItem("role") !=null){
      this.fetch();
    }
    this.medicineForm = this.formBuilder.group({
      name: ['',[Validators.required, Validators.minLength(3)]],
      description: ['',[Validators.required, Validators.minLength(10)]],
      image: ['',[Validators.required,RxwebValidators.extension({extensions:["jpeg"]})]],
      storeId: ['',[Validators.required]],
      price: ['', [Validators.required,Validators.min(0),Validators.max(9999999999)]]
    });
  }

  onFileUpload(event:any){
    this.file = event.target.files[0];
  }

  addMedicine(){
    if(this.medicineForm.valid){
      const data={...this.medicine,...this.medicineForm.value}
      data.image = this.file;
      console.log(data);
      this.dashboardService.addMedicine(data).subscribe({
        next: data=> {console.log(data);this.onSaveComplete(data);},
        error: err=> this.toastr.error('Medicine entry Failed! Please try again')
      });
    }
    else{
      this.toastr.error('Please correct the Validation errors');
    }
  }

  fetch(){
    this.dashboardService.getStore().subscribe({
      next: data=> {console.log(data); this.store=data; this.fetchagain();},
      error: err=> console.log(err)
    });
  }
  fetchagain(){
    this.dashboardService.getAllMedToStore().subscribe({
      next: data=> {
        console.log(data);
        const check = this.store.map(x=> x.storeId);
        this.medToStore=data.filter(x=>check.includes(x.storeId.storeId))
                            .map(x=> {
                              return {
                                name: x.medicineId.name,
                                description: x.medicineId.description,
                                price: x.medicineId.price,
                                storeName: x.storeId.name,
                                available: x.available? 'Available':'Not Available'
                              }
                            });
        console.log(this.medToStore);
        this.loadTable();
        },
      error: err=> console.log(err)
    });
  }

  loadTable(){
    this.dataSource = new MatTableDataSource<any>(this.medToStore);
    
    this.dataSource.sort = this.sort;
  }

  onSaveComplete(data: any){
    const medToStore={
      medicineId: data.medicineId,
      storeId: this.medicineForm.get('storeId').value
    }
    console.log(medToStore);
    this.dashboardService.addMedToStore(medToStore).subscribe({
      next: data=> {console.log(data); this.onComplete()},
      error: err=> this.toastr.error('Medicine entry Failed! Please try again')
    });
  }

  onComplete(){
    this.medicineForm.reset();
    window.location.reload();
  }

}
