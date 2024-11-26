import { Component } from '@angular/core';
import { AuthService } from "../service/auth.service";
import { SocialAuthService } from '@abacritt/angularx-social-login';
import { GoogleLoginProvider } from '@abacritt/angularx-social-login';
import { HttpClient } from '@angular/common/http';
import { AuthResponse } from '../service/auth-response.interface';
import { Route, Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username: string;
  password: string;

  constructor(private authService: AuthService,private socialAuthService: SocialAuthService,
    private http: HttpClient,private router: Router) {
    this.username=''
    this.password=''
  }

  login() {
      this.authService.login(this.username, this.password).subscribe(response => {
          localStorage.setItem('token', response.token); // Store token
          // Redirect or show success message
      });
  }
  loginWithGoogle() {
    this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(user => {
        const token = user.idToken;

        this.authService.loginWithGoogle(token).subscribe(
            (response: AuthResponse) => {
                localStorage.setItem('token', response.token); // Access token safely
                // Redirect or show success message
            },
            error => {
                console.error('Login failed', error);
            }
        );
    }).catch(err => {
        console.error(err);
    });
}
}

