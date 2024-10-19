import { Component, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { CartItem } from '../../../store/cart/cart.model';
import { Appstate } from '../../../store/app.state';
import { Store } from '@ngrx/store';
import {
  selectCartItems,
  selectCartTotal,
} from '../../../store/cart/cart.selector';
import { addItem, removeItem } from '../../../store/cart/cart.action';
import { CoreModule } from '../../../core/core.module';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-shopping-cart',
  standalone: true,
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.css',
  imports: [CoreModule, CommonModule, FontAwesomeModule],
})
export class ShoppingCartComponent {
  cartItems$: Observable<CartItem[]> = this.store.select(selectCartItems);
  cartTotal$: Observable<number> = this.store.select(selectCartTotal);

  constructor(private store: Store<Appstate>) {}

  addToCart(item: CartItem) {
    this.store.dispatch(addItem({ item }));
  }
  removeFromCart(id: number) {
    this.store.dispatch(removeItem({ id }));
  }
}
