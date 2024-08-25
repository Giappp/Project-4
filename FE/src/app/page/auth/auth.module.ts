import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { RegisterComponent } from './register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ConfirmPassValidatorDirective } from './register/Validator/confirm-pass-validator.directive';


@NgModule({
  declarations: [RegisterComponent, ConfirmPassValidatorDirective],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule
  ],
  exports: [RegisterComponent]
})
export class AuthModule { }
