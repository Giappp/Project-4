import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { confirmPassValidator } from '../validator/confirmPassValidator/confirm-pass-validator.directive';
import { phoneValidator } from '../validator/phoneValidator/phone-validator.directive';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent implements OnInit {
  signupForm!: FormGroup;
  showPassword = false;

  constructor(private fb: FormBuilder) {
    this.signupForm = this.fb.group(
      {
        username: ['', Validators.required],
        password: ['', [Validators.required]],
        confirmPassword: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        phone: [
          '',
          [Validators.required, Validators.maxLength(10), phoneValidator()],
        ],
        firstName: ['', [Validators.required]],
        lastName: ['', [Validators.required]],
      },
      { validators: confirmPassValidator }
    );
  }

  ngOnInit(): void {}

  toggleShowPassword(): void {
    this.showPassword = !this.showPassword;
  }

  signUp(): void {}

  get username() {
    return this.signupForm.get('username');
  }

  get phone() {
    return this.signupForm.get('phone');
  }

  get email() {
    return this.signupForm.get('email');
  }
  get firstName() {
    return this.signupForm.get('firstName');
  }
  get lastName() {
    return this.signupForm.get('lastName');
  }
  get password() {
    return this.signupForm.get('password');
  }
  get confirmPassword() {
    return this.signupForm.get('confirmPassword');
  }
}
