import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { ListClientsComponent } from './pages/list-clients/list-clients.component';

const routes: Routes = [
  { path: 'clients', component: ListClientsComponent },
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class ClientsRoutingModule { }
