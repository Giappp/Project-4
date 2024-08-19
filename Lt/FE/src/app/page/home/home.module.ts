import { NgModule } from '@angular/core';
import { CommonModule, NgFor } from '@angular/common';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './components/homepage/home.component';
import { ComponentsModule } from './components/components.module';




@NgModule({
  declarations: [
     ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    ComponentsModule
  ],
  exports: [
  ]
})
export class HomeModule { }
