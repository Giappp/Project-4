import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientsRoutingModule } from './clients-routing.module';

import { EditClientComponent } from './pages/edit-client/edit-client.component';
import { AddClientComponent } from './pages/add-client/add-client.component';
import { ListClientsComponent } from './pages/list-clients/list-clients.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    EditClientComponent,
    AddClientComponent,
    ListClientsComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ClientsRoutingModule,
  ],
  exports: [
    EditClientComponent,
    AddClientComponent,
    ListClientsComponent
  ]
})
export class ClientsModule { }
