import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { ListcartItemsComponent } from './pages/list-cart-items/list-cart-items.component';

const routes: Routes = [
  { path: 'cart-items', component: ListcartItemsComponent },

];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class cartItemsRoutingModule { }
