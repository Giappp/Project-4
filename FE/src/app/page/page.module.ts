import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeModule } from './home/home.module';
import { PageRoutingModule } from './page-routing.module';
import { HomeComponent } from './home/components';

@NgModule({
  declarations: [],
  imports: [CommonModule, HomeModule, PageRoutingModule],
})
export class PageModule {}
