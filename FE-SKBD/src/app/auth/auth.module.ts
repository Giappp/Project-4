import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';
import { PassValidatorDirective } from './validator/passwordValidator/pass-validator.directive';
import { LoginComponent } from './login/login.component';
import { SharedModule } from '../shared/shared.module';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { AuthRoutingModule } from './auth-routing.module';
import { RegisterComponent } from './register/register.component';
import { ConfirmPassValidatorDirective } from './validator/confirmPassValidator/confirm-pass-validator.directive';
import { PhoneValidatorDirective } from './validator/phoneValidator/phone-validator.directive';

@NgModule({
  declarations: [
    ConfirmPassValidatorDirective,
    PassValidatorDirective,
    PhoneValidatorDirective,
    LoginComponent,
    RegisterComponent,
  ],
  imports: [SharedModule, AuthRoutingModule, ReactiveFormsModule, ToastModule],
  exports: [LoginComponent, RegisterComponent],
  providers: [MessageService],
})
export class AuthModule {}
