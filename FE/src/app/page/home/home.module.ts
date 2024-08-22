import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  HomeComponent,
  AccessoryCollectionComponent,
  SummerCollectionComponent,
  WinterCollectionComponent,
} from './components/index';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [
    HomeComponent,
    AccessoryCollectionComponent,
    SummerCollectionComponent,
    WinterCollectionComponent,
  ],
  imports: [CommonModule, SharedModule],
})
export class HomeModule {}
