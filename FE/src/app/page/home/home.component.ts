import { Component, ViewChild } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { Observable } from 'rxjs';
import { NzCarouselComponent, NzCarouselModule } from 'ng-zorro-antd/carousel';
import { NzDemoBreadcrumbRouterComponent } from '../../shared/components/breadcrumb.component';
import { Category } from '../../model/category';
import { ProductService } from '../../products/services/product.service';
@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [
    CommonModule,
    NgOptimizedImage,
    NzCarouselModule,
    NzDemoBreadcrumbRouterComponent,
  ],
  providers: [],
})
export class HomeComponent {
  @ViewChild('carousel') carousel!: NzCarouselComponent;
  categories$!: Observable<Category[]>;
  carouselImageUrl!: string[];

  constructor(private productService: ProductService) {
    this.categories$ = this.productService.getAllProductsCategories();
    this.carouselImageUrl = [
      '/assets/images/17277697730331009.webp',
      '/assets/images/17150541229617388.webp',
      '/assets/images/17289654441295587.webp',
      '/assets/images/17291301779762643.webp',
    ];
  }
}
