import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Order } from '../../../interfaces/order';
import { Client } from '../../../interfaces/client';
import { OrderService } from '../../service/order.service';
import { ClientService } from '../../../clients/service/client.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-order',
  templateUrl: './edit-order.component.html',
  styleUrls: ['./edit-order.component.css']
})
export class EditOrderComponent implements OnInit {
  editOrderForm!: FormGroup;
  clients: Client[] = [];
  orderId: number = 0;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
      private orderService: OrderService,
      private clientService: ClientService,
      private route: ActivatedRoute,
      private router: Router
  ) { }

  ngOnInit(): void {
    this.orderId = this.route.snapshot.params['id'];
    this.initForm();
    this.loadClients();
    this.loadOrder();
  }

  initForm(): void {
    this.editOrderForm = new FormGroup({
      clientId: new FormControl('', Validators.required),
      state: new FormControl('', Validators.required),
      date: new FormControl({ value: '', disabled: true }), // Keeping date disabled
    });
  }

  loadClients(): void {
    this.clientService.getAllClients().subscribe(
        (clients: Client[]) => {
          this.clients = clients;
        },
        (error) => {
          console.error('Error fetching clients:', error);
        }
    );
  }

  loadOrder(): void {
    this.orderService.getOrderById(this.orderId).subscribe(
        (order: Order) => {
          this.editOrderForm.patchValue({
            clientId: order.client.id,
            state: order.state,
            date: order.date // Assuming you want to keep the date for reference
          });
        },
        (error) => {
          console.error('Error fetching order:', error);
        }
    );
  }

  updateOrder(): void {
    if (this.editOrderForm.valid) {
      const orderData = {
        idClient: this.editOrderForm.value.clientId,
        state: this.editOrderForm.value.state,
      };

      this.orderService.updateOrder(this.orderId, orderData).subscribe(
          (response: Order) => {
            this.successMessage = 'The order has been successfully updated!';
            this.errorMessage = null;
            setTimeout(() => {
              this.router.navigate(['/orders']);
            }, 2000);
          },
          (error) => {
            this.errorMessage = 'An error occurred while updating the order.';
            this.successMessage = null;
            console.error('Error updating order:', error);
          }
      );
    }
  }

  cancelUpdate(): void {
    // Navigate back to the list of orders
    this.router.navigate(['/orders']);
  }
}