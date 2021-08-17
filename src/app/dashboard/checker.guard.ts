import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserCheckerGuard implements CanActivate {

  constructor(private router: Router){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if(localStorage.getItem("role") != null && (localStorage.getItem("role") === "User" || localStorage.getItem("role") === "Owner")){
        return true;
      }
      this.router.navigate(['/Login']);
    return false;
  }
  
}
