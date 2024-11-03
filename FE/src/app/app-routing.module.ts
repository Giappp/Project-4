import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './page/home/home.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
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
    loadComponent: () =>
      import('./page/product-list/product-list.component').then(
        (c) => c.ProductListComponent
      ),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
