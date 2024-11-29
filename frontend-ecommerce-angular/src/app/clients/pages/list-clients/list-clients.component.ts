import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../service/client.service';
import { Client } from '../../../interfaces/client';

@Component({
  selector: 'app-list-clients',
  templateUrl: './list-clients.component.html',
  styleUrls: ['./list-clients.component.css'] // Corrected 'styleUrl' to 'styleUrls'
})
export class ListClientsComponent implements OnInit {
  clients: Client[] = [];

  constructor(private clientService: ClientService) { }

  ngOnInit(): void {
    this.getAllClients();
  }

  getAllClients(): void {
    this.clientService.getAllClients().subscribe({
      next: (clients: Client[]) => {
        this.clients = clients;
        // Uncomment to debug
        // console.log("clients:", clients);
      },
      error: (error) => {
        console.error('Error fetching clients:', error);
      }
    });
  }

  getClient(clientId: number): void {
    // Implement your logic to show client details, for example:
    // this.router.navigate(['/details-client', clientId]);
  }

  updateClient(clientId: number): void {
    // Implement your logic to redirect to the client update page, for example:
    // this.router.navigate(['/update-client', clientId]);
  }

  onDeleteClient(clientId: number, firstName: string, lastName: string): void {
    const confirmation = confirm(`Are you sure you want to delete ${lastName} ${firstName}?`);
    if (confirmation) {
      this.clientService.deleteClient(clientId).subscribe({
        next: () => {
          this.clients = this.clients.filter(client => client.id !== clientId);
          console.log("Client deleted successfully!");
        },
        error: (error) => {
          console.error("Error deleting client:", error);
        }
      });
    }
  }
}