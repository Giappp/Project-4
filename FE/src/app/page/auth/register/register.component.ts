import { User } from './../../../core/model/user';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { confirmPassValidator } from './Validator/confirm-pass-validator.directive';
import { passwordValidator } from './Validator/passwordValidator/pass-validator.directive';
import { phoneValidator } from './Validator/phoneValidator/phone-validator.directive';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {

  formcheck!: boolean;
  formRegisGroup!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.formRegisGroup = this.fb.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, passwordValidator()]],
      confirmPassword: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.maxLength(10), phoneValidator()]]
  }, {validators: confirmPassValidator});
  }


  triggerValidator() {
    if(!this.formRegisGroup.valid) {
      console.log("not valid");
      this.formcheck = true;
    }
  }

  regis() {
    console.log(this.formcheck);
  }

  get username() {
    return this.formRegisGroup.get('username');
  }

  get phone() {
    return this.formRegisGroup.get('phone') as FormControl;
  }

  get email() {
    return this.formRegisGroup.get('email') as FormControl;
  }
}

