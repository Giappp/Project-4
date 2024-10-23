import {
  afterNextRender,
  AfterViewInit,
  Component,
  CUSTOM_ELEMENTS_SCHEMA,
  ElementRef,
  ViewChild,
} from '@angular/core';
import { CommonModule, IMAGE_CONFIG, NgOptimizedImage } from '@angular/common';
import { FlowbiteService } from '../../../shared/services/flowbite.service';
import { Carousel } from 'flowbite';
import { ProductService } from '../../../products/services/product.service';
import { Observable } from 'rxjs';
import { Product } from '../../../products/models/product';
import { ProductCategory } from '../../../products/models/product-categories';
import { ProductCategoryService } from '../../../products/services/product-category.service';
@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [CommonModule, NgOptimizedImage],
  providers: [
    {
      provide: IMAGE_CONFIG,
      useValue: {
        placeholderResolution: 40,
      },
    },
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class HomeComponent {
  carousel!: Carousel;
  categories$!: Observable<ProductCategory[]>;
  categories!: ProductCategory[];
  @ViewChild('data-carousel-prev') $prevButton!: ElementRef;
  @ViewChild('data-carousel-next') $nextButton!: ElementRef;
  constructor(
    private flowbiteService: FlowbiteService,
    private productCategory: ProductCategoryService
  ) {
    this.categories$ = productCategory.getAllProducts();

    this.categories$.subscribe((val) => {
      this.categories = val;
    });
    afterNextRender(() => {
      this.flowbiteService.loadFlowbite((flowbite) => {
        console.log('loaded flowbite', flowbite);
      });
    });
  }
  initCarousel() {}
}
