import {
  AfterViewInit,
  Component,
  ElementRef, Inject,
  inject,
  OnInit, PLATFORM_ID,
  signal,
  viewChild,
} from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from '../../../core/auth/account.service';
import { LoginService } from './login.service';
import {isPlatformBrowser} from "@angular/common";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit, AfterViewInit {
  username = viewChild.required<ElementRef>('username');

  authenticationError = signal(false);
  loginForm: FormGroup;

  private accountService = inject(AccountService);
  private loginService = inject(LoginService);
  private router = inject(Router);


  constructor(private fb: FormBuilder,@Inject(PLATFORM_ID) private platformId: Object) {
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

  ngAfterViewInit(): void {
    this.username().nativeElement.focus();
  }

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.setItem('key', 'value');
    }
    this.accountService.identity().subscribe(() => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['']);
      }
    });
  }

  get userName() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  login(): void {
    this.loginService.login(this.loginForm.getRawValue()).subscribe({
      next: () => {
        this.authenticationError.set(false);
        if (!this.router.getCurrentNavigation()) {
          this.router.navigate(['']);
        }
      },
      error: () => this.authenticationError.set(true),
    });
  }
}
