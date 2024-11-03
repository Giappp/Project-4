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
import { map, Observable } from 'rxjs';
import { Category } from '../../model/category';
import { Gender } from '../../model/gender';
import { ProductService } from '../../products/services/product.service';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';

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
    NzMenuModule,
    NzDropDownModule,
  ],
})
export class HeaderComponent implements OnInit {
  @Input() items: string[] = [];
  cartItems$ = this.store.select(selectCartItems);
  categories$!: Observable<Category[]>;
  genders!: Observable<Gender[]>;
  uniqueLoai$!: Observable<string[]>;
  isBrowser: boolean = false;
  groupedCategories: { loai: string; items: Category[] }[] = [];

  constructor(
    library: FaIconLibrary,
    private store: Store,
    private productService: ProductService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    library.addIcons();
    this.categories$ = this.productService.getAllProductsCategories();
    this.genders = this.productService.getProductGender();
  }
  ngOnInit(): void {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }
  groupByLoai(categories: any[]): any {
    const categoryMap: Map<string, Category[]> = categories.reduce(
      (categoryMap: Map<string, Category[]>, category) => {
        const loai = category.loai;
        if (!categoryMap.has(loai)) {
          categoryMap.set(loai, []);
        }
        categoryMap.get(loai)?.push(category);
        return categoryMap;
      },
      new Map()
    );
    return this.convertMapToArray(categoryMap);
  }
  convertMapToArray(
    categoryMap: Map<string, Category[]>
  ): { loai: string; items: Category[] }[] {
    return Array.from(categoryMap.entries()).map(([loai, items]) => ({
      loai,
      items,
    }));
  }
}
