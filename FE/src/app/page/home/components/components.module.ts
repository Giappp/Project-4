import { NgModule } from '@angular/core';
import { CommonModule, NgFor } from '@angular/common';
import { AccessoryCollectionComponent } from './accessory-collection/accessory-collection.component';
import { SummerCollectionComponent } from './summer-collection/summer-collection.component';
import { WinterCollectionComponent } from './winter-collection/winter-collection.component';
import { HomeComponent } from './homepage/home.component';
import { SharedModule } from '../../../shared/shared.module';



@NgModule({
  declarations: [
    HomeComponent,
    AccessoryCollectionComponent,
    SummerCollectionComponent,
    WinterCollectionComponent,

  ],
  imports: [
    CommonModule,
    NgFor,
    SharedModule
  ],
  exports: [
    HomeComponent,
    AccessoryCollectionComponent,
    SummerCollectionComponent,
    WinterCollectionComponent,

  ]
})
export class ComponentsModule { }
