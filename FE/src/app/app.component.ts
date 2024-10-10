import { Component, inject, OnInit } from '@angular/core';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from './config/font-awesome-icons';
import { FlowbiteService } from './shared/services/flowbite.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  private iconLibrary = inject(FaIconLibrary);

  constructor(private flowbiteService: FlowbiteService) {
    this.iconLibrary.addIcons(...fontAwesomeIcons);
  }
  ngOnInit(): void {
    this.flowbiteService.loadFlowbite(() => {});
  }
}
