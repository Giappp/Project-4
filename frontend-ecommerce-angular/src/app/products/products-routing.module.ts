import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';


import { ListProductsComponent } from './pages/list-products/list-products.component';
import { AddProductComponent } from './pages/add-product/add-product.component';
import { EditProductComponent } from './pages/edit-product/edit-product.component';
import { ProductGuard } from '../product.guard';

const routes: Routes = [
  { path: '', component: ListProductsComponent},
  { path: 'addProduct', component: AddProductComponent, canActivate: [ProductGuard] },
  { path: 'edit-product/:id', component: EditProductComponent, canActivate: [ProductGuard] }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class ProductsRoutingModule { }
