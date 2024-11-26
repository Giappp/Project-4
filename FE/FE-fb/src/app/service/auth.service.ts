// src/app/services/auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthResponse } from './auth-response.interface';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = 'http://localhost:8083/api/auth'; // Your backend API URL

    constructor(private http: HttpClient) {}

    login(username: string, password: string): Observable<any> {
        return this.http.post(`${this.apiUrl}/login`, { username, password });
    }
    loginWithGoogle(token: string): Observable<AuthResponse> {
      return this.http.post<AuthResponse>(`${this.apiUrl}/google`, { token });
  }
}
