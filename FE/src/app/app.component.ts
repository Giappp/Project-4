import { Component, inject } from '@angular/core';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from './config/font-awesome-icons';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  private iconLibrary = inject(FaIconLibrary);

  constructor() {
    this.iconLibrary.addIcons(...fontAwesomeIcons);
  }
}
