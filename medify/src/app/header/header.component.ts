import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatMenu } from '@angular/material/menu';
import { MatDrawer, MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { UserService } from '../auth/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  @Input() inputSideNav: MatDrawer;

  mat: MatMenu;
  title: String;
  logedIn: Boolean = false;
  constructor(private router: Router,
    private userService: UserService,
    private localStorage : LocalStorageService) { }

  ngOnInit(): void {
    if(localStorage.getItem("role") !=null){
      this.logedIn = true;
      this.title = this.localStorage.retrieve("username");
    }
  }

  logout() : any{
    
    this.userService.logout().subscribe({
      next: data=> {console.log(data); this.localStorage.clear();localStorage.clear();window.location.reload();},
      error: err=> console.log(err)
    });
  }

}
