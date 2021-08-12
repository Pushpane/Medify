import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IAddress } from './address';
import { IMedicine } from './medicine';
import { IMedToStore } from './medToStore';
import { IStore } from './store';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  //url for http request
  private userUrl:string =environment.apiUrl;
  constructor(private http: HttpClient,private localStorage : LocalStorageService) { } 

  getStore(): Observable<any>{
    //creating url   
    const url = this.userUrl + "/owner/getStoreByUser/"+this.localStorage.retrieve("username");

    //returning observable
    return this.http.get<any>(url);
  }

  getAllAddressByUser(): Observable<IAddress[]>{
    const url = this.userUrl + "/user/getAddressByUser/"+ this.localStorage.retrieve("username");
    return this.http.get<IAddress[]>(url);
  }

  getAllMedToStore(): Observable<IMedToStore[]>{
    const url = this.userUrl + "/user/getAllMedicine";
    return this.http.get<IMedToStore[]>(url);
  }

  addStore(store: any): Observable<any>{
    const url = this.userUrl + "/owner/addStore";
    const data = {...store, email: this.localStorage.retrieve('username')};
    return this.http.post<any>(url,data);
  }

  addAddress(address: any): Observable<any>{
    const url = this.userUrl + "/user/addAddress";
    const data = {...address, email: this.localStorage.retrieve('username')};
    return this.http.post<any>(url,data);
  }

  addMedicine(medicine: IMedicine): Observable<any>{
    const url = this.userUrl + "/owner/addMedicine";
    const data = new FormData();
    data.append('image',medicine.image);
    data.append('name',medicine.name);
    data.append('description',medicine.description);
    data.append('price', medicine.price);

    return this.http.post<any>(url,data);
  }

  addMedToStore(data: any): Observable<any>{
    const url = this.userUrl + "/user/addMedicineToStore";

    return this.http.post<any>(url,data);
  }
}
