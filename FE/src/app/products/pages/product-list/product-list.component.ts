import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product';
import { ProductServiceService } from '../../services/product-service.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css',
})
export class ProductListComponent implements OnInit {
  constructor(private productService: ProductServiceService) {}
  ngOnInit(): void {
    this.productService.getAllProducts().subscribe((data: Product[]) => {
      this.products = data;
    });
  }
  products: Product[] = [];
}
