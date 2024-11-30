import { Injectable } from '@angular/core';
import { User } from '../interfaces/User';
import { Router } from '@angular/router';
import {HttpClient, HttpResponse} from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { CartService } from '../cart/service/cart.service';
import { catchError } from 'rxjs/operators';
import {Observable, of, throwError} from 'rxjs';
import {PasswordResetResponse} from "../interfaces/PasswordResetResponse";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiURL: string = 'http://localhost:8083/api/auth';
  private token?: string;
  private helper = new JwtHelperService();

  public loggedUser?: string;
  public isLoggedIn: Boolean = false;
  public roles: string[] = [];

  public registeredUser: User = new User();

  constructor(
      private router: Router,
      private http: HttpClient,
      private cartService: CartService
  ) {}

  // Authentication methods
  login(user: User) {
    return this.http.post<User>(this.apiURL + '/login', user, { observe: 'response' });
  }

  // Logout method
  logout() {
    this.loggedUser = undefined;
    this.roles = [];
    this.token = undefined;
    this.isLoggedIn = false;
    localStorage.removeItem('jwt');
    localStorage.removeItem('isLoggedIn');
    this.cartService.clearAllcartItems().subscribe(); // Clear the cart items
    this.router.navigate(['/login']);
  }

  // Token management methods
  saveToken(jwt: string) {
    this.token = jwt;
    localStorage.setItem('jwt', jwt);
    this.isLoggedIn = true;
    localStorage.setItem('isLoggedIn', 'true');
    this.decodeJWT();
  }

  loadToken() {
    this.token = localStorage.getItem('jwt') || undefined;
    this.decodeJWT();
  }

  getToken(): string | undefined {
    return this.token;
  }

  decodeJWT() {
    if (!this.token) return;
    const decodedToken = this.helper.decodeToken(this.token);
    this.roles = decodedToken.roles || [];
    this.loggedUser = decodedToken.sub;
  }

  isTokenExpired(): boolean {
    // Ensure the token is not undefined
    if (!this.token) {
      return true; // Consider it expired if there is no token
    }
    return this.helper.isTokenExpired(this.token); // Now this.token is guaranteed to be a string
  }

  // Role and user management methods
  isAdmin(): boolean {
    return this.roles.includes('ADMIN');
  }

  setLoggedUserFromLocalStorage() {
    this.isLoggedIn = true;
  }

  registerUser(user: User) {
    return this.http.post<User>(this.apiURL + '/register', user,
        { observe: 'response' });
  }

  setRegisteredUser(user: User) {
    this.registeredUser = user;
  }

  getRegisteredUser(): User {
    return this.registeredUser;
  }

  validateEmail(code : string){
    return this.http.get<User>(this.apiURL+'/verifyEmail/'+code);
  }

  // Google login
  loginWithGoogle() {
    return this.http.get<{ token: string }>(`${this.apiURL}/oauth2/login`, { observe: 'response' })
        .pipe(catchError(err => {
          console.error('Google login failed', err);
          return of(null); // Handle error appropriately
        }));
  }


  requestPasswordReset(email: string): Observable<PasswordResetResponse> {
    return this.http.post<PasswordResetResponse>(`${this.apiURL}/forgot-password`, { email })
        .pipe(catchError(err => {
          console.error('Error sending password reset link', err);
          return throwError(err); // Rethrow the error for handling in the component
        }));
  }
}