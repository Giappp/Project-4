import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EditCategorieComponent } from './pages/edit-categorie/edit-categorie.component';
import { AddCategorieComponent } from './pages/add-categorie/add-categorie.component';
import { ListCategoriesComponent } from './pages/list-categories/list-categories.component';
import { CategoriesRoutingModule } from './categories-routing.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    EditCategorieComponent,
    AddCategorieComponent,
    ListCategoriesComponent
  ],
  exports: [
    EditCategorieComponent,
    AddCategorieComponent,
    ListCategoriesComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CategoriesRoutingModule
  ]
})
export class CategoriesModule { }
