import { Component, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Router, RouterModule } from '@angular/router';
import { Product } from '../../../../products/models/product';
import { ProductServiceService } from '../../../../products/services/product-service.service';

@Component({
  selector: 'app-winter-collection',
  templateUrl: './winter-collection.component.html',
  styleUrl: './winter-collection.component.css',
})
export class WinterCollectionComponent {
  products$!: Observable<Product[]>;
  products!: Product[];
  private router = inject(Router);
  constructor(private productService: ProductServiceService) {
    this.products$ = productService.getAllProducts();

    this.products$.subscribe((val) => {
      this.products = val;
    });
  }
  goDetail(item: any): void {
    this.router.navigate(['/product-detail', item.id]);
  }
}
