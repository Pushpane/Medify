import {Component, Inject, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {LoginComponent} from "../login/login.component";

@Component({
  selector: 'app-medify-dashboard',
  templateUrl: './medify-dashboard.component.html',
  styleUrls: ['./medify-dashboard.component.css']
})
export class MedifyDashboardComponent implements OnInit {

  private loginDialog: MatDialog;

  constructor(loginDialog: MatDialog) {
    this.loginDialog = loginDialog;
  }

  ngOnInit(): void {

  }

  showLoginPopUp() {
    const dialogConfig = new MatDialogConfig();
    this.loginDialog.open(LoginComponent,dialogConfig);
  }
}
