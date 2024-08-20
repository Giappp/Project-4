import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeModule } from './home/home.module';
import { RegisComponent } from './auth/regis/regis.component';
import { LoginComponent } from './auth/login/login.component';



@NgModule({
  declarations: [
    RegisComponent,
    LoginComponent
  ],
  imports: [
    CommonModule,
    HomeModule
  ]
})
export class PageModule { }
