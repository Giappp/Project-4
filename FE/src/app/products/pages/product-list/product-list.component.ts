import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { NzDemoBreadcrumbRouterComponent } from '../../../shared/components/breadcrumb.component';
import { Product } from '../../../model/product';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css',
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  constructor(private productService: ProductService) {}
  ngOnInit(): void {
    this.productService.getAllProducts().subscribe((data: Product[]) => {
      this.products = data;
    });
  }
}
