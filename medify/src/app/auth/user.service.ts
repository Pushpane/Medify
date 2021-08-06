import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ISignup } from './signup';
import { catchError, tap, map } from 'rxjs/operators';
import { ILoginResponse } from './login-response';
import { ILogin } from './login';
import { LocalStorageService } from 'ngx-webstorage';
import { IForgotPassword } from './forgot-password';
import { IUpdatePassword } from './update-password';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  //url for http request
  private userUrl:string =environment.apiUrl;

  constructor(private http: HttpClient,private localStorage : LocalStorageService) { }

  registerUser(user: ISignup): Observable<any>{
    //creating url   
    const url = this.userUrl + "/auth/signup";

    //returning observable
    return this.http.post<any>(url, user);
  }

  forgotPassword(passwordChanger: IForgotPassword): Observable<any>{
    //creating url   
    const url = this.userUrl + "/auth/forgotPassword";

    //returning observable
    return this.http.post<any>(url, passwordChanger);
  }

  updatePassword(passwordChanger: IUpdatePassword): Observable<any>{
    //creating url   
    const url = this.userUrl + "/auth/updatePassword";

    //returning observable
    return this.http.post<any>(url, passwordChanger);
  }

  login(userLoginObject: ILogin): Observable<boolean>{
    //creating url
    const url = this.userUrl + "/auth/login";

    //returning observable
    return this.http.post<ILoginResponse>(url, userLoginObject)
      .pipe(
        map(data => {
          this.localStorage.store('authenticationToken', data.authenticationToken);
          this.localStorage.store('username', data.username);
          this.localStorage.store('refreshToken', data.refreshToken);
          this.localStorage.store('expiresAt', data.expiresAt);
          return true;
        }));
  }

  //handling error
  handleError(err: HttpErrorResponse) {
    //variable to build error string
    let errorMessage: string = '';

    //if error is instance of errorevent build msg else build msg with error code
    if(err.error instanceof ErrorEvent){
      errorMessage=`An error occured: ${err.error.message}`;
    }else{
      errorMessage=`Server returned code: ${err.status}, error message is: ${err.error.Message}, ${err.message}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }

  refreshToken() {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getUserName()
    }
    return this.http.post<ILoginResponse>(this.userUrl+'/auth/refresh/token',
      refreshTokenPayload)
      .pipe(tap(response => {
        this.localStorage.store('authenticationToken', response.authenticationToken);
        this.localStorage.store('expiresAt', response.expiresAt);
      }));
  }

  getJwtToken() {
    return this.localStorage.retrieve('authenticationToken');
  }

  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  getUserName() {
    return this.localStorage.retrieve('username');
  }

  getExpirationTime() {
    return this.localStorage.retrieve('expiresAt');
  }
}
