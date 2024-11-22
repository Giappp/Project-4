<<<<<<< Updated upstream
import { Component, Inject, Input, OnInit, PLATFORM_ID } from '@angular/core';
=======
import {
  Component,
  inject,
  Inject,
  OnInit,
  PLATFORM_ID,
  OnDestroy,
} from '@angular/core';
>>>>>>> Stashed changes
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { FaIconLibrary, FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { Store } from '@ngrx/store';
import { selectCartItems } from '../../store/cart/cart.selector';
import { ShoppingCartComponent } from '../../cart/components/shopping-cart/shopping-cart.component';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Category } from '../../model/category';
import { Gender } from '../../model/gender';
import { ProductService } from '../../shared/services/product.service';
import { MegaMenuModule } from 'primeng/megamenu';
import { MegaMenuItem, MenuItem } from 'primeng/api';
<<<<<<< Updated upstream
=======
import { AccountService } from '../../core/auth/account.service';
import { LoginService } from '../../page/auth/login/login.service';
>>>>>>> Stashed changes

@Component({
  standalone: true,
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'], // Corrected from styleUrl to styleUrls
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
<<<<<<< Updated upstream
export class HeaderComponent implements OnInit {
=======
export class HeaderComponent implements OnInit, OnDestroy {
  account = inject(AccountService).trackCurrentAccount();
  authService = inject(LoginService);
>>>>>>> Stashed changes
  cartItems$ = this.store.select(selectCartItems);
  categories$!: Observable<Category[]>;
  genders$!: Observable<Gender[]>;
  uniqueCategory$!: Observable<string[]>;
  isBrowser: boolean = false;
  menuItems: MegaMenuItem[] = [];
  subMenuItems: MenuItem[] = [];
  genders: Gender[] = [];
  private destroy$ = new Subject<void>(); // Subject to manage unsubscription

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
    this.genders$
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (genders) => {
          // Check if genders is an array
          if (Array.isArray(genders)) {
            this.menuItems = genders.map((gender) => ({
              label: gender.name,
              root: true,
              items: this.groupCategoriesByProductCategory(gender.categories || []), // Ensure categories is an array
            }));
          } else {
            console.error('Expected genders to be an array, but got:', genders);
          }
        },
        error: (err) => {
          console.error('Error fetching genders:', err);
        },
      });
  }

  private groupCategoriesByProductCategory(categories: Category[]): MenuItem[][] {
    if (!Array.isArray(categories) || categories.length === 0) {
      console.warn('No categories provided or categories is not an array');
      return [];
    }

    const map: Map<string, string[]> = categories.reduce(
      (acc: Map<string, string[]>, category: Category) => {
        if (!acc.has(category.productCategory)) {
          acc.set(category.productCategory, []);
        }
        acc.get(category.productCategory)?.push(category.categoryName);
        return acc;
      },
      new Map()
    );

    const menu: MenuItem[][] = [];
    let tempPair: MenuItem[] = [];

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

      if (tempPair.length === 2) {
        menu.push(tempPair);
        tempPair = [];
      }
    });

    if (tempPair.length) {
      menu.push(tempPair);
    }

    return menu;
  }
<<<<<<< Updated upstream
=======
  logout(): void {
    this.authService.logout();
  }

  ngOnDestroy() {
    this.destroy$.next(); // Trigger unsubscription
    this.destroy$.complete(); // Complete the subject
  }
>>>>>>> Stashed changes
}
