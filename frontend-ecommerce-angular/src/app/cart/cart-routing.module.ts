import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { ListcartComponent } from './pages/list-cart/list-cart.component';

const routes: Routes = [
  { path: 'cart', component: ListcartComponent },
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class cartRoutingModule { }
