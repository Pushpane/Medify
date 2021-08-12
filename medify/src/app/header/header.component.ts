import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDrawer, MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  @Input() inputSideNav: MatDrawer;

  title: String;
  logedIn: Boolean = false;
  constructor(private router: Router,
    private localStorage : LocalStorageService) { }

  ngOnInit(): void {
    if(localStorage.getItem("role") !=null){
      this.logedIn = true;
      this.title = this.localStorage.retrieve("username");
    }
  }

  logout() : void{
    this.localStorage.clear();
    localStorage.clear();
    console.log('Loged Out!');
    this.router.navigate(['/']);
  }

}
