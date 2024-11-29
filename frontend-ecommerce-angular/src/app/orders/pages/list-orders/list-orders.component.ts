import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../service/order.service';
import { Order } from '../../../interfaces/order';
import { Router } from '@angular/router';
import { CartService } from "../../../cart/service/cart.service";

@Component({
  selector: 'app-list-orders',
  templateUrl: './list-orders.component.html',
  styleUrls: ['./list-orders.component.css']
})
export class ListOrdersComponent implements OnInit {
  orders: Order[] = [];
  totalPrice!: number;
  showOrderTable: boolean = false;

  constructor(
      private orderService: OrderService,
      private cartService: CartService,
      private router: Router
  ) {}

  ngOnInit(): void {
    this.getAllOrders();
    this.getTotalPrice();
  }

  getAllOrders(): void {
    this.orderService.getAllOrders().subscribe({
      next: (orders: Order[]) => {
        this.orders = orders;
      },
      error: (error) => {
        console.error('Error fetching orders:', error);
      }
    });
  }

  getTotalPrice(): void {
    this.cartService.getTotalPrice().subscribe({
      next: (totalPrice: number) => {
        this.totalPrice = totalPrice;
      },
      error: (error) => {
        console.error('Error fetching total price:', error);
      }
    });
  }

  payOrder(): void {
    console.log('Order paid successfully!');
    this.showOrderTable = true;
    // Implement additional logic for processing the order payment if necessary
  }

  viewOrderDetails(orderId: number): void {
    // Implement logic to view order details
  }

  editOrder(orderId: number): void {
    console.log("Order ID:", orderId);
    // Redirect to the edit order page
    this.router.navigate(['orders/edit-order', orderId]);
  }

  deleteOrder(orderId: number): void {
    // Implement delete order logic here
  }
}