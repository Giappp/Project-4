import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './page/home/home.component';
import { HomeLayoutComponent } from './layout/home-layout/home-layout.component';

const routes: Routes = [
  {
    path: '',
    component: HomeLayoutComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent },
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
    ],
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./page/auth/auth.module').then((m) => m.AuthModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
