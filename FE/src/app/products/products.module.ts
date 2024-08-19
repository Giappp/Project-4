import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductListComponent } from './pages/product-list/product-list.component';
import { ProductDetailComponent } from './pages/product-detail/product-detail.component';
import { RouterModule } from '@angular/router';
import { ProductRoutingModule } from './products-routing.module';
import { ProductServiceService } from './services/product-service.service';

@NgModule({
  declarations: [ProductListComponent, ProductDetailComponent],
  imports: [CommonModule, RouterModule, ProductRoutingModule],
})
export class ProductsModule {}
