import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ForbiddenComponent } from './errors/forbidden/forbidden.component';
import { RegisterComponent } from './register/register.component';
import { VerifyEmailComponent } from './verif-email/verify-email.component';
import {ForgotPasswordComponent} from "./login/forgot-password-component/forgot-password-component.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'app-forbidden', component: ForbiddenComponent },
  { path: 'products', loadChildren: () => import('./products/products.module').then(m => m.ProductsModule) },
  { path: 'register', component: RegisterComponent },
  { path: 'verifEmail/:email', component: VerifyEmailComponent },
  { path: 'orders', loadChildren: () => import('./orders/orders.module').then(m => m.OrdersModule) },
  { path: 'cart', loadChildren: () => import('./cart/cart.module').then(m => m.cartModule) },
  { path: 'clients', loadChildren: () => import('./clients/clients.module').then(m => m.ClientsModule) },



];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
