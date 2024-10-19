import { Component, CUSTOM_ELEMENTS_SCHEMA, Inject, PLATFORM_ID, } from '@angular/core';
import { SummerCollectionComponent } from '../summer-collection/summer-collection.component';
import { AccessoryCollectionComponent } from '../accessory-collection/accessory-collection.component';
import { WinterCollectionComponent } from '../winter-collection/winter-collection.component';
import { CommonModule, IMAGE_CONFIG, isPlatformBrowser, NgOptimizedImage, } from '@angular/common';
@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [
    SummerCollectionComponent,
    AccessoryCollectionComponent,
    WinterCollectionComponent,
    CommonModule,
    NgOptimizedImage,
  ],
  providers: [
    {
      provide: IMAGE_CONFIG,
      useValue: {
        placeholderResolution: 40,
      },
    },
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class HomeComponent {
  img = 'assets/images/hero_img/hero.jpg';
  // Using Swiper library, which is purely client-side so this variable is a flag to render the swiper only in client-side
  isBrowser: boolean;

  constructor(@Inject(PLATFORM_ID) private platformId: object) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }
}
