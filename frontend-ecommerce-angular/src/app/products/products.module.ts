import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListProductsComponent } from './pages/list-products/list-products.component';
import { AddProductComponent } from './pages/add-product/add-product.component';
import { EditProductComponent } from './pages/edit-product/edit-product.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ProductsRoutingModule } from './products-routing.module';
import { PipesModule } from "../pipes/pipes.module";



@NgModule({
    declarations: [
        ListProductsComponent,
        AddProductComponent,
        EditProductComponent
    ],
    exports: [
        ListProductsComponent,
        AddProductComponent,
        EditProductComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        ProductsRoutingModule,
        PipesModule
    ]
})
export class ProductsModule { }
