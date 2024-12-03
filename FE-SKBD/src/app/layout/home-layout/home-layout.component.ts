import { Component, inject } from '@angular/core';
import { AuthFacade } from '../../auth/store/auth.fascade';

@Component({
  selector: 'app-home-layout',
  templateUrl: './home-layout.component.html',
  styleUrl: './home-layout.component.css',
})
export class HomeLayoutComponent {
  private readonly authFacade = inject(AuthFacade);

  readonly authUser$ = this.authFacade.authUser$;

  protected onLogout() {
    this.authFacade.logout();
  }
}
