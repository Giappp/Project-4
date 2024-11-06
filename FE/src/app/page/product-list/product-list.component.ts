import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../shared/services/product.service';
import { Product } from '../../model/product';
import { CommonModule } from '@angular/common';
import { BreadcrumbComponentDemo } from '../../shared/components/breadcrumb/breadcrumb.component';
import { SliderModule } from 'primeng/slider';
import { DropdownModule } from 'primeng/dropdown';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { Category } from '../../model/category';
import { CheckboxModule } from 'primeng/checkbox';
import { RadioButtonModule } from 'primeng/radiobutton';
@Component({
  selector: 'app-product-list',
  standalone: true,
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css',
  imports: [
    CommonModule,
    BreadcrumbComponentDemo,
    SliderModule,
    DropdownModule,
    FormsModule,
    ReactiveFormsModule,
    CheckboxModule,
    RadioButtonModule,
  ],
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  filters = {
    priceRange: '',
    category: '',
    productColor: '',
  }

  filteredProducts: any[] = [];
  formGroup!: FormGroup;
  priceRange: number[] = [0, 500000];
  selectedColor: string | null = null;
  categories: any[] = [];
  constructor(private productService: ProductService) {}
  ngOnInit(): void {
    this.formGroup = new FormGroup({
      selectedCategory: new FormControl(),
    });
    this.productService.getAllProducts().subscribe((data: Product[]) => {
      this.products = data;
      this.filteredProducts = data;
    });
    this.productService
      .getAllProductsCategories()
      .subscribe((data: Category[]) => {
        this.categories = data.map((c) => ({
          name: c.categoryName,
          key: c.id,
        }));
      });
  }
  filterProducts() {}
}
