import { User } from '../../../model/user';
import { Component, inject, OnInit, signal } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { confirmPassValidator } from './Validator/confirm-pass-validator.directive';
import { passwordValidator } from './Validator/passwordValidator/pass-validator.directive';
import { phoneValidator } from './Validator/phoneValidator/phone-validator.directive';
import { AccountService } from '../../../core/auth/account.service';
import { Router } from '@angular/router';
import { LoginService } from '../login/login.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent implements OnInit {
  signupForm!: FormGroup;

  showPassword = false;
  authenticationError = signal(false);

  private accountService = inject(AccountService);
  private router = inject(Router);
  private loginService = inject(LoginService);

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

  ngOnInit(): void {
    this.accountService.identity().subscribe(() => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['']);
      }
    });
  }

  toggleShowPassword(): void {
    this.showPassword = !this.showPassword;
  }

  signUp(): void {
    this.loginService.register(this.signupForm.getRawValue()).subscribe({
      next: () => {
        this.authenticationError.set(false);
        if (!this.router.getCurrentNavigation()) {
          this.router.navigate(['']);
        }
      },
      error: () => {
        this.authenticationError.set(true);
      },
    });
  }

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
