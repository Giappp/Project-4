import { User } from './../../../core/model/user';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { confirmPassValidator } from './Validator/confirm-pass-validator.directive';

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
      password: ['', [Validators.required]],
      confirmPassword: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.maxLength(11), Validators.minLength(11),this.phoneValidator]]
  }, {validators: confirmPassValidator});
  }

  passwordValidator(control: AbstractControl): { [key: string] : boolean } | null {
    const passwordPattern = /(?=.*[A-Z])(?=.*\d).+/;
    if(!passwordPattern.test(control.value)) {
      console.log(control.value + " day la check regex");
      return { 'invalid password' : true};
    }
    console.log('vao day');
    return null;
  }

  phoneValidator(control: AbstractControl): { [key: string] : boolean } | null {
    const phonePattern = /^\d{11}$/;
    if(!phonePattern.test(control.value)) {
      return {'invalid phone' : true};
    }
    return null;
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

