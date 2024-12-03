import {
  Component,
  inject,
  Inject,
  Input,
  OnInit,
  output,
  PLATFORM_ID,
  Signal,
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
import { selectCartItems } from '../../cart/store/cart.selector';
import { ShoppingCartComponent } from '../../cart/components/shopping-cart/shopping-cart.component';
import { last, Observable } from 'rxjs';
import { Category } from '../../model/category';
import { MegaMenuModule } from 'primeng/megamenu';
import { MegaMenuItem, MenuItem } from 'primeng/api';
import { AccountService } from '../../core/auth/account.service';
import * as authJwtService from '../../core/auth/auth-jwt.service';
import { Account } from '../../core/auth/account.model';
import { AuthUser } from '../../auth/store/auth.model';

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
  @Input({ required: true })
  authUser: AuthUser | null | undefined = null;

  readonly logout = output<void>();

  cartItems$ = this.store.select(selectCartItems);
  categories$!: Observable<Category[]>;
  uniqueLoai$!: Observable<string[]>;
  menuItems: MegaMenuItem[] = [];
  subMenuItems: MenuItem[] = [];

  constructor(
    library: FaIconLibrary,
    private store: Store,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    library.addIcons();
  }
  ngOnInit() {}

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
}
