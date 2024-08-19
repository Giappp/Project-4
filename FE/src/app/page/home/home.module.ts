import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent, AccessoryCollectionComponent, SummerCollectionComponent, WinterCollectionComponent } from './components/index'
import { SharedModule } from '../../shared/shared.module';




@NgModule({
  declarations: [
    HomeComponent,
    AccessoryCollectionComponent,
    SummerCollectionComponent,
    WinterCollectionComponent,
    ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    SharedModule
  ],
  exports: [
    HomeComponent,
    AccessoryCollectionComponent,
    SummerCollectionComponent,
    WinterCollectionComponent,
  ]
})
export class HomeModule { }
