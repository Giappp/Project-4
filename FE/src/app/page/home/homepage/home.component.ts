import { Component } from '@angular/core';
import { SummerCollectionComponent } from '../summer-collection/summer-collection.component';
import { AccessoryCollectionComponent } from '../accessory-collection/accessory-collection.component';
import { WinterCollectionComponent } from '../winter-collection/winter-collection.component';
@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [
    SummerCollectionComponent,
    AccessoryCollectionComponent,
    WinterCollectionComponent,
  ],
})
export class HomeComponent {
  title = 'front';
}
