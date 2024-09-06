import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../../../../products/models/product';
import { ProductServiceService } from '../../../../products/services/product-service.service';

@Component({
  selector: 'app-accessory-collection',
  templateUrl: './accessory-collection.component.html',
  styleUrl: './accessory-collection.component.css',
})
export class AccessoryCollectionComponent {
  products$!: Observable<Product[]>;
  products!: Product[];
  constructor(private productService: ProductServiceService) {
    this.products$ = productService.getAllProducts();

    this.products$.subscribe((val) => {
      this.products = val;
    });
  }
}
