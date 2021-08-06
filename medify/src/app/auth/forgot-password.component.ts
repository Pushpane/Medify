import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from './user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  tokenForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private usersService: UserService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    //build the form
    this.tokenForm = this.formBuilder.group({
      email: ['',[Validators.required, Validators.minLength(5),Validators.email]],
    });
  }

  PasswordChanger(): void {
    if(this.tokenForm.valid){
      const data = {...{},...this.tokenForm.value};
      this.usersService.forgotPassword(data)
        .subscribe({
          next: ()=> this.onSaveComplete(),
          error: err=> this.toastr.error('Password Reset Failed! Please try again')
        });
    }
    else{
      this.toastr.error('Please correct the Validation errors');
    }
  }

  onSaveComplete(): void {
    this.tokenForm.reset();
    this.toastr.success('Reset Successful! Please check your mail for Validation token.');
    this.router.navigate(['/UpdatePassword']);
  }
}
