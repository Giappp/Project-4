import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ListOrdersComponent } from './pages/list-orders/list-orders.component';
import { AddOrderComponent } from './pages/add-order/add-order.component';
import { EditOrderComponent } from './pages/edit-order/edit-order.component';

const routes: Routes = [
  { path: '', component: ListOrdersComponent },
  { path: 'add', component: AddOrderComponent },
  { path: 'edit-order/:id', component: EditOrderComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class OrdersRoutingModule { }
