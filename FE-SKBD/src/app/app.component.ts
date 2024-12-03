import { Component, Inject, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from './config/font-awesome-icons';
import { isPlatformBrowser } from '@angular/common';
import { ApplicationConfigService } from './core/config/application-config.service';
import { AuthService } from './auth/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
  title: string = 'Shopping with Skibidi';
  isBrowser: boolean = false;
  private iconLibrary = inject(FaIconLibrary);

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private authService: AuthService
  ) {
    this.iconLibrary.addIcons(...fontAwesomeIcons);
  }
  ngOnInit(): void {
    this.isBrowser = isPlatformBrowser(this.platformId);
    if (typeof window !== 'undefined') {
      window.addEventListener('load', () => {
        this.authService.init();
      });
    }
  }
}
