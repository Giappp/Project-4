import { inject, Injectable } from '@angular/core';
import { AccountService } from '../../../core/auth/account.service';
import { AuthServerProvider } from '../../../core/auth/auth-jwt.service';
import { Login } from '../../../model/login';
import { mergeMap, Observable } from 'rxjs';
import { Account } from '../../../core/auth/account.model';

@Injectable({ providedIn: 'root' })
export class LoginService {
  private accountService = inject(AccountService);
  private authServerProvider = inject(AuthServerProvider);
  login(credentials: Login): Observable<Account | null> {
    return this.authServerProvider
      .login(credentials)
      .pipe(mergeMap(() => this.accountService.identity(true)));
  }
  logout(): void {
    this.authServerProvider
      .logout()
      .subscribe({ complete: () => this.accountService.authenticate(null) });
  }
}
