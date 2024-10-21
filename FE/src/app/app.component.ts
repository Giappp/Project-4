import {
  afterNextRender,
  Component,
  Inject,
  inject,
  OnInit,
  PLATFORM_ID,
} from '@angular/core';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from './config/font-awesome-icons';
import { FlowbiteService } from './shared/services/flowbite.service';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  title: string = 'Shopping with Skibidi';
  isBrowser: boolean = false;
  private iconLibrary = inject(FaIconLibrary);

  constructor(
    private flowbiteService: FlowbiteService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.iconLibrary.addIcons(...fontAwesomeIcons);
    afterNextRender(() => {
      this.flowbiteService.loadFlowbite(() => {});
    });
  }
  ngOnInit(): void {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }
}
