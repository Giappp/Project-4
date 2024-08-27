import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  HomeComponent,
  AccessoryCollectionComponent,
  SummerCollectionComponent,
  WinterCollectionComponent,
} from './components/index';
import { SharedModule } from '../../shared/shared.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    HomeComponent,
    AccessoryCollectionComponent,
    SummerCollectionComponent,
    WinterCollectionComponent,
  ],
  imports: [CommonModule, SharedModule, RouterModule],
})
export class HomeModule {}
