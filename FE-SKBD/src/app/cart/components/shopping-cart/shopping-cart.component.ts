import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { Observable } from 'rxjs';
import { CartItem } from '../../store/cart.model';
import { Appstate } from '../../../app.state';
import { Store, StoreModule } from '@ngrx/store';
import { selectCartItems, selectCartTotal } from '../../store/cart.selector';
import { addItem, removeItem } from '../../store/cart.action';
import { CoreModule } from '../../../core/core.module';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { BadgeModule } from 'primeng/badge';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { cartReducer } from '../../store/cart.reducer';
@Component({
  selector: 'app-shopping-cart',
  standalone: true,
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.css',
  imports: [
    CoreModule,
    CommonModule,
    FontAwesomeModule,
    BadgeModule,
    OverlayPanelModule,
  ],
})
export class ShoppingCartComponent {
  cartItems$: Observable<CartItem[]> = this.store.select(selectCartItems);
  cartTotal$: Observable<number> = this.store.select(selectCartTotal);
  constructor(
    private store: Store<Appstate>,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  addToCart(item: CartItem) {
    this.store.dispatch(addItem({ item }));
  }
  removeFromCart(id: number) {
    this.store.dispatch(removeItem({ id }));
  }
}
