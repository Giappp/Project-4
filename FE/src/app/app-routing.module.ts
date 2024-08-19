import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'product/:id',
    loadChildren: () => import('./components/product-detail/product-detail.module').then(m => m.ProductDetailModule)
  },
  { path: '', redirectTo: '/product/1', pathMatch: 'full' },
  { path: '*', redirectTo: '/product/1' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
