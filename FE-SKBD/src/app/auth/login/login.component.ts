import {
  AfterViewInit,
  Component,
  ElementRef,
  inject,
  OnInit,
  signal,
  viewChild,
} from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import * as AuthSelectors from '../store/auth.selectors';
import { Store } from '@ngrx/store';
import { MessageService } from 'primeng/api';
import { LoginActions } from '../store/auth.action';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  providers: [MessageService],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  readonly authUser$ = this.store.select(AuthSelectors.selectAuthUser);
  readonly isLoggedIn$ = this.store.select(AuthSelectors.selectIsLoggedIn);
  readonly isLoaddingLogin$ = this.store.select(
    AuthSelectors.selectIsLoadingLogin
  );

  readonly hasLoginError$ = this.store.select(AuthSelectors.selectLoginError);

  constructor(private fb: FormBuilder, private store: Store) {
    this.loginForm = this.fb.group({
      username: new FormControl('', {
        nonNullable: true,
        validators: [Validators.required],
      }),
      password: new FormControl('', {
        nonNullable: true,
        validators: [Validators.required, Validators.minLength(6)],
      }),
      rememberMe: new FormControl(false, {
        nonNullable: true,
        validators: [Validators.required],
      }),
    });
  }

  ngOnInit(): void {}

  get userName() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  login(): void {
    const { username, password } = this.loginForm.getRawValue();
    this.store.dispatch(
      LoginActions.request({
        username: username as string,
        password: password as string,
        rememberMe: true,
      })
    );
  }
}
