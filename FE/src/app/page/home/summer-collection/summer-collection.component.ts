import { Component, inject, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../../../products/models/product';
import { ProductService } from '../../../products/services/product.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-summer-collection',
  templateUrl: './summer-collection.component.html',
  styleUrl: './summer-collection.component.css',
  imports: [RouterModule, CommonModule],
})
export class SummerCollectionComponent implements OnInit {
  product$!: Observable<Product[]>;
  product!: Product[];
  private router = inject(Router);

  constructor(private productService: ProductService) {}
  ngOnInit(): void {
    this.product$ = this.productService.getAllProducts();
    this.product$.subscribe((val) => {
      this.product = val;
      console.log(this.product);
    });
  }
  goDetail(item: any): void {
    this.router.navigate(['/product-detail', item.id]);
  }
}
