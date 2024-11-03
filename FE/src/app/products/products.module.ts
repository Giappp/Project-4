import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductListComponent } from './pages/product-list/product-list.component';
import { RouterModule } from '@angular/router';
import { ProductRoutingModule } from './products-routing.module';
import { NzDemoBreadcrumbRouterComponent } from '../shared/components/breadcrumb.component';

@NgModule({
  declarations: [ProductListComponent],
  imports: [
    CommonModule,
    RouterModule,
    ProductRoutingModule,
    NzDemoBreadcrumbRouterComponent,
  ],
})
export class ProductsModule {}
