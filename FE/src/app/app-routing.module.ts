import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./page/home/homepage/home.component').then(
        (c) => c.HomeComponent
      ),
  },
  {
    path: '',
    loadComponent: () =>
      import('./layout/header/header.component').then((m) => m.HeaderComponent),
    outlet: 'navbar',
  },
  {
    path: 'about',
    loadComponent: () =>
      import('./page/about/about.component').then((c) => c.AboutComponent),
  },
  {
    path: 'products',
    loadChildren: () =>
      import('./products/products.module').then((m) => m.ProductsModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
