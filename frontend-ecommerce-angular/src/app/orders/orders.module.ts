import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { OrdersRoutingModule } from './orders-routing.module';

import { ListOrdersComponent } from './pages/list-orders/list-orders.component';
import { AddOrderComponent } from './pages/add-order/add-order.component';
import { EditOrderComponent } from './pages/edit-order/edit-order.component';
import { PipesModule } from "../pipes/pipes.module";

@NgModule({
    declarations: [
        ListOrdersComponent,
        AddOrderComponent,
        EditOrderComponent
    ],
    exports: [
        ListOrdersComponent,
        AddOrderComponent,
        EditOrderComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        OrdersRoutingModule,
        PipesModule
    ]
})
export class OrdersModule { }
