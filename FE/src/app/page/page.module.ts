import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeModule } from './home/home.module';
import { PageRoutingModule } from './page-routing.module';
import { HomeComponent } from './home/components';
import { AuthModule } from './auth/auth.module';

@NgModule({
  declarations: [],
  imports: [CommonModule, HomeModule, PageRoutingModule,AuthModule],
})
export class PageModule {}
