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
import { provideStore, StoreModule } from '@ngrx/store';
import { AUTH_FEATURE_KEY, authReducer } from './store/auth.reducer';
import { EffectsModule, provideEffects } from '@ngrx/effects';
import { AuthEffects } from './store/auth.effect';
import { authServiceInitProvider } from './services/auth.service';
import { AuthInterceptor } from './interceptors/auth.interceptors';

@NgModule({
  declarations: [
    ConfirmPassValidatorDirective,
    PassValidatorDirective,
    PhoneValidatorDirective,
    LoginComponent,
    RegisterComponent,
  ],
  imports: [
    SharedModule,
    AuthRoutingModule,
    ReactiveFormsModule,
    ToastModule,
    StoreModule.forFeature(AUTH_FEATURE_KEY, authReducer),
  ],
  exports: [LoginComponent, RegisterComponent],
  providers: [MessageService, provideEffects(AuthEffects), AuthInterceptor],
})
export class AuthModule {}
