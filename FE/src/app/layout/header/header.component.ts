import { Component, Inject, Input, OnInit, PLATFORM_ID } from '@angular/core';
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
import { Gender } from '../../model/gender';
import { ProductService } from '../../shared/services/product.service';
import { MegaMenuModule } from 'primeng/megamenu';
import { MegaMenuItem, MenuItem } from 'primeng/api';

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
  cartItems$ = this.store.select(selectCartItems);
  categories$!: Observable<Category[]>;
  genders$!: Observable<Gender[]>;
  uniqueLoai$!: Observable<string[]>;
  isBrowser: boolean = false;
  menuItems: MegaMenuItem[] = [];
  subMenuItems: MenuItem[] = [];
  genders: Gender[] = [];

  constructor(
    library: FaIconLibrary,
    private store: Store,
    private productService: ProductService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    library.addIcons();
    this.categories$ = this.productService.getAllProductsCategories();
    this.genders$ = this.productService.getProductGender();
  }
  ngOnInit() {
    // Fetch the genders data as an observable
    this.genders$ = this.productService.getProductGender(); // Fetch data from service

    // Subscribe to the observable to populate menuItems with grouped categories
    this.genders$.subscribe((genders) => {
      this.menuItems = genders.map((gender) => ({
        label: gender.name,
        root: true,
        items: this.groupCategoriesByLoai(gender.categories!),
      }));
    });
  }

  private groupCategoriesByLoai(categories: Category[]): MenuItem[][] {
    const map: Map<string, string[]> = categories.reduce(
      (acc: Map<string, string[]>, category: Category) => {
        if (!acc.has(category.loai)) {
          acc.set(category.loai, []);
        }
        acc.get(category.loai)?.push(category.categoryName);
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

  login() {
    this.router.navigate(['/login']);
  }
  signIn() {
    this.router.navigate(['/register']);
  }
}
