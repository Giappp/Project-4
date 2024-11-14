import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { RegisterComponent } from './register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ConfirmPassValidatorDirective } from './register/Validator/confirm-pass-validator.directive';
import { PassValidatorDirective } from './register/Validator/passwordValidator/pass-validator.directive';
import { PhoneValidatorDirective } from './register/Validator/phoneValidator/phone-validator.directive';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    ConfirmPassValidatorDirective,
    PassValidatorDirective,
    PhoneValidatorDirective,
    LoginComponent,
    RegisterComponent,
  ],
  imports: [CommonModule, AuthRoutingModule, ReactiveFormsModule],
  exports: [LoginComponent, RegisterComponent],
})
export class AuthModule {}
