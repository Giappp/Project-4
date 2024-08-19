import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../../../../products/models/product';
import { ProductServiceService } from '../../../../products/services/product-service.service';

@Component({
  selector: 'app-summer-collection',
  templateUrl: './summer-collection.component.html',
  styleUrl: './summer-collection.component.css'
})
export class SummerCollectionComponent {
  product$!: Observable<Product[]>;
  product!: Product[];

  constructor(private productService : ProductServiceService) {
    this.product$ = productService.getAllProducts();
    this.product$.subscribe(val => {
      this.product = val;
      console.log(this.product);
    })
  }

}