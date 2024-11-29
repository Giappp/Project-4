import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { cartRoutingModule } from './cart-routing.module';
import { EditcartComponent } from './pages/edit-cart/edit-cart.component';
import { AddcartComponent } from './pages/add-cart/add-cart.component';
import { ListcartComponent } from './pages/list-cart/list-cart.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PipesModule } from '../pipes/pipes.module';

@NgModule({
  declarations: [
    EditcartComponent,
    AddcartComponent,
    ListcartComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    cartRoutingModule,
    PipesModule
  ],
  exports: [
    EditcartComponent,
    AddcartComponent,
    ListcartComponent
  ]
})
export class cartModule { }
