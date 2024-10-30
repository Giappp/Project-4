import { Component, CUSTOM_ELEMENTS_SCHEMA, ViewChild } from '@angular/core';
import { CommonModule, IMAGE_CONFIG, NgOptimizedImage } from '@angular/common';
import { Observable } from 'rxjs';
import { ProductCategory } from '../../../products/models/product-categories';
import { ProductCategoryService } from '../../../products/services/product-category.service';
import { NzCarouselComponent, NzCarouselModule } from 'ng-zorro-antd/carousel';
@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [CommonModule, NgOptimizedImage, NzCarouselModule],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class HomeComponent {
  @ViewChild('carousel') carousel!: NzCarouselComponent;
  categories$!: Observable<ProductCategory[]>;
  categories!: ProductCategory[];
  carouselImageUrl = [
    '/assets/images/17277697730331009.webp',
    '/assets/images/17150541229617388.webp',
    '/assets/images/17289654441295587.webp',
    '/assets/images/17291301779762643.webp',
  ];

  constructor(private productCategory: ProductCategoryService) {
    this.categories$ = this.productCategory.getAllProducts();
    this.categories$.subscribe((val) => {
      this.categories = val;
    });
  }
}
