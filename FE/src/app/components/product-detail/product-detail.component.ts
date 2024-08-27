import { Component, OnInit } from '@angular/core';
import { ProductDetailService } from '../service/product-detail.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent implements OnInit {
  product: any;
  selectedColor: string | null = null;
  selectedSize: string | null = null;
  quantity: number = 1;
  selectedCombo: any;

  constructor(
    private productService: ProductDetailService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const productId = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProductById(productId).subscribe(data => {
      this.product = data;
    });
  }

  addToCart() {
    if (!this.selectedColor) {
      alert("Please choose color");
    } else if (!this.selectedSize) {
      alert("Please choose your size");
    } else {
      alert("Cart added successfully")
    }
    return;
  }

  buyNow() {
    if (!this.selectedColor) {
      alert("Please choose color");
    } else if (!this.selectedSize) {
      alert("Please choose your size");
    } else {
      alert("Buy successfully")
    }
  }

  decreaseQuantity() {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  increaseQuantity() {
    this.quantity++;
  }
}
