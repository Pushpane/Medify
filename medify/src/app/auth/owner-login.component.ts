import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from './user.service';

@Component({
  selector: 'app-owner-login',
  templateUrl: './owner-login.component.html',
  styleUrls: ['./owner-login.component.css']
})
export class OwnerLoginComponent implements OnInit {
  loginForm: FormGroup;
  hide: boolean;

  constructor(private formBuilder: FormBuilder,
    private usersService: UserService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    //build the form
    this.loginForm = this.formBuilder.group({
      email: ['',[Validators.required, Validators.minLength(5),Validators.email]],
      password: ['',[Validators.required, Validators.minLength(8),Validators.pattern("^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")]]
    });
  }

  Login(): void {
    if(this.loginForm.valid){
      const data = {...{},...this.loginForm.value};
      this.usersService.login(data)
        .subscribe({
          next: ()=> this.onSaveComplete(),
          error: err=> this.toastr.error('Login Failed! Please try again')
        });
    }
    else{
      this.toastr.error('Please correct the Validation errors');
    }
  }

  onSaveComplete(): void {
    this.loginForm.reset();
    localStorage.setItem('role','Owner');
    this.toastr.success('Login Successful.');
    this.router.navigate(['/OwnerDashboard']);
  }
}
