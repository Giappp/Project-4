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
import { combineLatest } from 'rxjs';
import { AuthFacade } from '../store/auth.fascade';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  providers: [MessageService],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  private readonly authFacade = inject(AuthFacade);

  readonly vm$ = combineLatest({
    isLoading: this.authFacade.isLoadingLogin$,
    showLoginError: this.authFacade.hasLoginError$,
  });

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

  ngOnInit(): void {
    if(this.authFacade.isLoggedIn$){
      
    }
  }

  get userName() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  login(): void {
    const { username, password, rememberMe } = this.loginForm.value;
    this.authFacade.login(
      username as string,
      password as string,
      rememberMe as boolean
    );
  }
}
