import { Component, OnInit } from '@angular/core';
import { CartService } from '../../service/cart.service';
import { cart } from '../../../interfaces/cart';
import { Router } from '@angular/router';
import { cartItem } from '../../../interfaces/cart-item';

@Component({
  selector: 'app-list-cart',
  templateUrl: './list-cart.component.html',
  styleUrls: ['./list-cart.component.css']
})
export class ListcartComponent implements OnInit {
  cart!: cart;
  totalPrice!: number;
  totalItems: number = 0;

  constructor(
    private CartService: CartService,
    private router: Router,

  ) { }

  ngOnInit(): void {
    this.getcartById();
    this.getTotalPrice();

  }



  getcartById(): void {
    this.CartService.getcartById(1).subscribe(
      {
        next: (cart: cart) => {
          this.cart = cart;
          this.totalPrice = this.cart.totalPrice;
          this.totalItems = this.cart.totalItems;

          // console.log("totalItems in ListcartComponent : ", this.totalItems);
        },
        error: (error) => {
          console.error('Error fetching cart:', error);
        }
      }
    );
  }



  getTotalPrice(): void {
    this.CartService.getTotalPrice().subscribe({
      next: (totalPrice: number) => {
        this.totalPrice = totalPrice;
        console.log("Total Price: ", totalPrice);
      },
      error: (error) => {
        console.error('Error fetching total price:', error);
      }
    });
  }

  addItemTocart(productID: number, quantity: number): void {
    const cartItem: cartItem = this.createcartItem(productID, quantity);

    this.CartService.addItemTocart(cartItem).subscribe({
      next: (addedItem: cartItem) => {
        this.getcartById();
        this.getTotalPrice();
      },
      error: (error) => {
        console.error('Error adding item to cart:', error);
      }
    });
  }


  removeItemFromcart(productID: number, quantity: number): void {
    const cartItem: cartItem = this.createcartItem(productID, quantity);

    console.log('this.cart.cartItems.length :', this.cart.cartItems.length);

    this.CartService.removeItemFromcart(cartItem).subscribe({
      next: () => {
        this.getcartById();
        this.getTotalPrice();
        
      },
      error: (error) => {
        console.error('Error removing item from cart:', error);
      }
    });
  }



  createcartItem(productID: number, quantity: number): cartItem {
    const cartItem: cartItem = {
      cart: { id: 1, namecart: '', totalPrice: 0, totalItems: 0, cartItems: [] },
      product: {
        idProd: productID,
        category: { idCat: 1, nameCat: "Category Name", descriptionCat: "Category Description" },
        nameProd: '',
        imageUrl: '',
        price: 0,
        rating: 0,
        date: new Date()
      },
      quantity: quantity,
      id: 0,
      totalExcludeTaxe: 0,
      totalWithTaxe: 0
    };
    return cartItem;
  }

  validateOrder(): void {
    this.router.navigate(['/orders']);

    console.log('Order placed successfully !');

  }


}
