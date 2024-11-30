import {
  Component,
  inject,
  Inject,
  Input,
  OnInit,
  PLATFORM_ID,
} from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import {
  FaIconLibrary,
  FontAwesomeModule,
} from '@fortawesome/angular-fontawesome';
import { Store } from '@ngrx/store';
import { selectCartItems } from '../../store/cart/cart.selector';
import { ShoppingCartComponent } from '../../cart/components/shopping-cart/shopping-cart.component';
import { last, Observable } from 'rxjs';
import { Category } from '../../model/category';
import { MegaMenuModule } from 'primeng/megamenu';
import { MegaMenuItem, MenuItem } from 'primeng/api';
import { AccountService } from '../../core/auth/account.service';
import { AuthServerProvider } from '../../core/auth/auth-jwt.service';
import { LoginService } from '../../page/auth/login/login.service';

@Component({
  standalone: true,
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    SharedModule,
    FontAwesomeModule,
    ShoppingCartComponent,
    MegaMenuModule,
  ],
})
export class HeaderComponent implements OnInit {
  account = inject(AccountService).trackCurrentAccount();

  authService = inject(LoginService);
  cartItems$ = this.store.select(selectCartItems);
  categories$!: Observable<Category[]>;
  uniqueLoai$!: Observable<string[]>;
  isBrowser: boolean = false;
  menuItems: MegaMenuItem[] = [];
  subMenuItems: MenuItem[] = [];

  constructor(
    library: FaIconLibrary,
    private store: Store,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    library.addIcons();
  }
  ngOnInit() {
    // Fetch the genders data as an observable
    // Subscribe to the observable to populate menuItems with grouped categories
  }

  private groupCategoriesByLoai(categories: Category[]): MenuItem[][] {
    const map: Map<string, string[]> = categories.reduce(
      (acc: Map<string, string[]>, category: Category) => {
        if (!acc.has(category.productType)) {
          acc.set(category.productType, []);
        }
        acc.get(category.productType)?.push(category.categoryName);
        return acc;
      },
      new Map()
    );

    const menu: MenuItem[][] = [];
    let tempPair: MenuItem[] = []; // Temporary array to hold each pair

    map.forEach((value: string[], key: string) => {
      const menuItem: MenuItem = {
        label: key,
        subRoot: true,
        items: value.map((categoryName) => ({
          label: categoryName,
        })),
      };

      if (menuItem.items!.length <= 5) {
        tempPair.push(menuItem);
      } else {
        menu.push([menuItem]);
      }

      // If `tempPair` has 2 items, push it to `menu` and reset
      if (tempPair.length === 2) {
        menu.push(tempPair);
        tempPair = []; // Reset the pair array
      }
    });

    if (tempPair.length) {
      menu.push(tempPair);
    }

    return menu;
  }
  logout(): void {
    this.authService.logout();
  }
}
