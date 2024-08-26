import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  baseUrl!: string;

  constructor(private httpClient : HttpClient) {
    this.baseUrl = "http://localhost:3000/";
  }

  public getAllUser() {};

  public insertUser(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.baseUrl}user`, user, {headers: this.getHeader()});
  }
  
  private getHeader()
  {
    const header = new HttpHeaders()
    .set('Content-Type', 'application/json');
    return header;
  }
}
