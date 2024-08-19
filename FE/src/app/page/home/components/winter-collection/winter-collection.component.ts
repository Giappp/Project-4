import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../../../../products/models/product';
import { ProductServiceService } from '../../../../products/services/product-service.service';

@Component({
  selector: 'app-winter-collection',
  templateUrl: './winter-collection.component.html',
  styleUrl: './winter-collection.component.css'
})
export class WinterCollectionComponent {
products$!: Observable<Product[]>;
  products!: Product[];
  constructor(private productService: ProductServiceService) {
    this.products$ = productService.getAllProducts();

    this.products$.subscribe(val => {
      this.products = val;
    })
  }
}
