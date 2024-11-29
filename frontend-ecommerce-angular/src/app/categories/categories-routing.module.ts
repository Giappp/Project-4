import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { ListCategoriesComponent } from './pages/list-categories/list-categories.component';
import { AddCategorieComponent } from './pages/add-categorie/add-categorie.component';
import { EditCategorieComponent } from './pages/edit-categorie/edit-categorie.component';

const routes: Routes = [
  { path: 'categories', component: ListCategoriesComponent },
  { path: 'categories/add', component: AddCategorieComponent },
  { path: 'categories/edit/:id', component: EditCategorieComponent },
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class CategoriesRoutingModule { }
