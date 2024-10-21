import {
  afterNextRender,
  AfterViewInit,
  Component,
  ElementRef,
  Inject,
  OnInit,
  PLATFORM_ID,
  ViewChild,
} from '@angular/core';
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
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { Dropdown, DropdownOptions } from 'flowbite';
import { FlowbiteService } from '../../../shared/services/flowbite.service';

@Component({
  selector: 'app-shopping-cart',
  standalone: true,
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.css',
  imports: [CoreModule, CommonModule, FontAwesomeModule],
})
export class ShoppingCartComponent implements AfterViewInit {
  cartItems$: Observable<CartItem[]> = this.store.select(selectCartItems);
  cartTotal$: Observable<number> = this.store.select(selectCartTotal);
  isOpenCartDropDown = false;
  dropdown!: Dropdown;

  @ViewChild('dropdownMenu') dropdownMenu!: ElementRef;
  @ViewChild('dropdownBtn') dropdownBtn!: ElementRef;

  constructor(
    private store: Store<Appstate>,
    private flowbiteService: FlowbiteService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}
  ngAfterViewInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      this.initDropdown(flowbite);
    });
  }

  addToCart(item: CartItem) {
    this.store.dispatch(addItem({ item }));
  }
  removeFromCart(id: number) {
    this.store.dispatch(removeItem({ id }));
  }
  initDropdown(flowbite: any): void {
    const $targetEl = this.dropdownMenu.nativeElement;
    const $triggerEl = this.dropdownBtn.nativeElement;

    if ($targetEl && $triggerEl) {
      const options = {
        placement: 'bottom',
        triggerType: 'click',
        offsetSkidding: 0,
        offsetDistance: 10,
        delay: 300,
        onHide: () => (this.isOpenCartDropDown = false),
        onShow: () => (this.isOpenCartDropDown = true),
      };

      // Initialize the dropdown using Flowbite
      this.dropdown = new flowbite.Dropdown($targetEl, $triggerEl, options);
    }
  }
}
