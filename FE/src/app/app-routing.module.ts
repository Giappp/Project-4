import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './page/home/components/homepage/home.component';

const routes: Routes = [
  // {
  //   path: 'products',
  //   loadChildren: () =>
  //     import('./products/products.module').then((m) => m.ProductsModule),
  // },
  {
    path: '',
    component: HomeComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
