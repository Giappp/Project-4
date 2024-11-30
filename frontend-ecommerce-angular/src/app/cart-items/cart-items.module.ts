import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { cartItemsRoutingModule } from './cart-items-routing.module';

import { EditcartItemComponent } from './pages/edit-cart-item/edit-cart-item.component';
import { AddcartItemComponent } from './pages/add-cart-item/add-cart-item.component';
import { ListcartItemsComponent } from './pages/list-cart-items/list-cart-items.component';

@NgModule({
  declarations: [
    EditcartItemComponent,
    AddcartItemComponent,
    ListcartItemsComponent
  ],
  imports: [
    CommonModule,
    cartItemsRoutingModule
  ],
  exports: [
    EditcartItemComponent,
    AddcartItemComponent,
    ListcartItemsComponent
  ]
})
export class cartItemsModule { }
