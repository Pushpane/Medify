import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ISignup } from './signup';
import { UserService } from './user.service';

@Component({
  selector: 'app-admin-signup',
  templateUrl: './admin-signup.component.html',
  styleUrls: ['./admin-signup.component.css']
})
export class AdminSignupComponent implements OnInit {
  signupForm: FormGroup;
  signupPayload: ISignup;

  constructor(private formBuilder: FormBuilder,
    private usersService: UserService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.signupForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.pattern('[a-zA-Z][a-zA-Z ]+[a-zA-Z]$')]],
      email: ['', [Validators.required, Validators.email, Validators.minLength(5)]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern("^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")]],
      phone: ['', [Validators.required, Validators.min(1000000000), Validators.max(9999999999)]],
    })
  }

  Signup(): void {
    if (this.signupForm.valid) {
      this.signupPayload = { ...this.signupPayload, ...this.signupForm.value };
      this.signupPayload.role = 'Admin';
      this.usersService.registerUser(this.signupPayload)
        .subscribe({
          next: () => this.onSaveComplete(),
          error: err => this.toastr.error('Registration Failed! Please try again.')
        });
    }
    else{
      this.toastr.error('Please correct the Validation errors');
    }
  }

  //after save is complete do routing
  onSaveComplete(): void {
    this.signupForm.reset();
    this.toastr.success("Activation Email Sent, Please Activate your account.")
    this.router.navigate(['/AdminLogin']);
  }

}
