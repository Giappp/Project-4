import { Component, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../../../products/models/product';
import { ProductServiceService } from '../../../products/services/product-service.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-accessory-collection',
  templateUrl: './accessory-collection.component.html',
  styleUrl: './accessory-collection.component.css',
  imports: [RouterModule, CommonModule],
})
export class AccessoryCollectionComponent {
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
