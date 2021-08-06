import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from './user.service';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.css']
})
export class UpdatePasswordComponent implements OnInit {
  tokenForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private usersService: UserService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    //build the form
    this.tokenForm = this.formBuilder.group({
      token: ['',[Validators.required, Validators.minLength(36),Validators.maxLength(36)]],
      password: ['',[Validators.required, Validators.minLength(8),Validators.pattern("^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")]]
    });
  }

  PasswordChanger(): void {
    if(this.tokenForm.valid){
      const data = {...{},...this.tokenForm.value};
      this.usersService.updatePassword(data)
        .subscribe({
          next: ()=> this.onSaveComplete(),
          error: err=> this.toastr.error('Password Update Failed! Please try again')
        });
    }
    else{
      this.toastr.error('Please correct the Validation errors');
    }
  }

  onSaveComplete(): void {
    this.tokenForm.reset();
    this.toastr.success('Password Reset Successful! Login with new Password');
    this.router.navigate(['/']);
  }
}
