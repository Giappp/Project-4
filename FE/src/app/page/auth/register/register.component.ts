import { User } from '../../../model/user';
import { Component, OnInit } from '@angular/core';
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
import { randomInt, randomUUID } from 'crypto';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent implements OnInit {
  formcheck!: boolean;
  formRegisGroup!: FormGroup;
  user = {} as User;
  regisOb!: Observable<User>;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.formRegisGroup = this.fb.group(
      {
        username: ['', Validators.required],
        password: ['', [Validators.required, passwordValidator()]],
        confirmPassword: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        phone: [
          '',
          [Validators.required, Validators.maxLength(10), phoneValidator()],
        ],
      },
      { validators: confirmPassValidator }
    );
  }

  triggerValidator() {
    if (!this.formRegisGroup.valid) {
      console.log('not valid');
      this.formcheck = true;
    } else {
      console.log('valid');
      this.formcheck = false;
    }
  }

  regis() {
    if (this.formcheck) {
      console.log('cant regis');
    } else {
      this.user.id = 12;
      this.user.username = this.username?.value;
      this.user.email = this.email.value;
      this.user.phone = this.phone.value;
      this.regisOb.subscribe((val) => {
        console.log('success full');
      });
    }
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
