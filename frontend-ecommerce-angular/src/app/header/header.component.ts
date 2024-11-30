import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart/service/cart.service';
import { cart } from '../interfaces/cart'; // Ensure consistent capitalization
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  cart!: cart; // Ensure correct casing for Cart
  totalPrice: number = 0;
  totalItems: number = 0;

  constructor(
      private cartService: CartService, // Use camelCase for service names
      private router: Router,
      public authService: AuthService,
      private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    const isloggedin = localStorage.getItem('isloggedIn');
    const loggedUser = localStorage.getItem('loggedUser');

    this.authService.loadToken();
    if (this.authService.getToken() == null || this.authService.isTokenExpired()) {
      this.router.navigate(['/login']);
    }

    this.authService.setLoggedUserFromLocalStorage();
    this.getCartById();
  }

  isLoggedIn(): boolean {
    const token = this.authService.getToken();
    return token !== null && !this.authService.isTokenExpired();
  }

  getCartById(): void {
    this.cartService.getcartById(1).subscribe({
      next: (cart: cart) => {
        this.cart = cart;
        // console.log('cart:', this.cart);
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error fetching cart:', error.message); // Logging the error for debugging
      }
    });
  }

  reloadCart(): void {
    window.location.reload(); // Consider replacing with a more Angular-friendly approach
  }

  onLogout(): void {
    this.toastr.success('Logout successful!');
    this.authService.logout(); // Navigate to login after logout
  }

  clearAllCartItems(): void {
    this.cartService.clearAllcartItems().subscribe({
      next: () => {
        console.log('All cart items cleared.');
        this.reloadCart();
      },
      error: (error) => {
        console.error('Error clearing cart items:', error);
      }
    });
  }
}
// import { HttpErrorResponse } from '@angular/common/http';
// import { Component, OnInit } from '@angular/core';
// import { BasketService } from '../baskets/service/basket.service';
// import { Basket } from '../interfaces/basket';
// import { Router } from '@angular/router';
// import { AuthService } from '../services/auth.service';
// import { ToastrService } from 'ngx-toastr';
//
// @Component({
//   selector: 'app-header',
//   templateUrl: './header.component.html',
//   styleUrls: ['./header.component.css']
// })
// export class HeaderComponent implements OnInit {
//   basket!: Basket;
//   totalPrice: number = 0;
//   totalItems: number = 0;
//
//   constructor(
//       private basketService: BasketService,
//       private router: Router,
//       public authService: AuthService,
//       private toastr: ToastrService
//   ) { }
//
//   ngOnInit(): void {
//     const isloggedin = localStorage.getItem('isloggedIn');
//     const loggedUser = localStorage.getItem('loggedUser');
//
//     this.authService.loadToken();
//     if (this.authService.getToken() == null || this.authService.isTokenExpired()) {
//       this.router.navigate(['/login']);
//     }
//
//     // Without backend
//     if (isloggedin !== "true") {
//       this.router.navigate(['/login']);
//     } else {
//       this.authService.setLoggedUserFromLocalStorage();
//     }
//
//     this.getBasketById();
//   }
//
//   getBasketById(): void {
//     this.basketService.getBasketById(1).subscribe({
//       next: (basket: Basket) => {
//         this.basket = basket;
//         // console.log('Basket:', this.basket);
//       },
//       error: (error: HttpErrorResponse) => {
//         // console.error('Error fetching basket:', error.message);
//       }
//     });
//   }
//
//   reloadBasket(): void {
//     window.location.reload();
//   }
//
//   onLogout(): void {
//     this.toastr.success('Logout successful!');
//     this.clearAllBasketItems();
//     this.authService.logout();
//   }
//
//   clearAllBasketItems(): void {
//     this.basketService.clearAllBasketItems().subscribe({
//       next: () => {
//         console.log('All basket items cleared.');
//         this.reloadBasket();
//       },
//       error: (error) => {
//         console.error('Error clearing basket items:', error);
//       }
//     });
//   }
// }