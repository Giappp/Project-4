import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BreadcrumbComponentDemo } from '../../shared/components/breadcrumb/breadcrumb.component';
import { SliderModule } from 'primeng/slider';
import { DropdownModule } from 'primeng/dropdown';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { Category } from '../../model/category';
import { CheckboxModule } from 'primeng/checkbox';
import { RadioButtonModule } from 'primeng/radiobutton';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { ProductFilter } from './product-filter';
import { ProductService } from './product.service';
import { FilterOptions } from './filter-options';
import { Product } from './product.interface';
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
  filterOptions: FilterOptions = { sizes: [], colors: [], types: [] };
  filterForm!: FormGroup;
  currentPage = 1;
  pageSize = 10;
  totalItems = 0;
  totalPages = 0;
  selectedSizes: string[] = [];
  selectedColors: string[] = [];

  categories: any[] = [];
  constructor(private productService: ProductService, private fb: FormBuilder) {
    this.filterForm = this.fb.group({
      minPrice: [''],
      maxPrice: [''],
      type: [''],
    });
  }
  ngOnInit(): void {
    this.loadFilterOptions();

    // Subscribe to form changes
    this.filterForm.valueChanges
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe(() => {
        this.currentPage = 1;
        this.loadProducts();
      });

    this.loadProducts();
  }

  loadFilterOptions() {
    this.productService.getFilterOptions().subscribe((options) => {
      this.filterOptions = options;
    });
  }
  loadProducts() {
    const filter: ProductFilter = {
      page: this.currentPage,
      pageSize: this.pageSize,
      ...this.filterForm.value,
    };

    if (this.selectedSizes.length) {
      filter.sizes = this.selectedSizes;
    }

    if (this.selectedColors.length) {
      filter.colors = this.selectedColors;
    }

    this.productService.getProducts(filter).subscribe((response) => {
      this.products = response.items;
      this.totalItems = response.total;
      this.totalPages = Math.ceil(this.totalItems / this.pageSize);
    });
  }
  onSizeChange(event: Event) {
    const checkbox = event.target as HTMLInputElement;
    if (checkbox.checked) {
      this.selectedSizes.push(checkbox.value);
    } else {
      this.selectedSizes = this.selectedSizes.filter(
        (size) => size !== checkbox.value
      );
    }
    this.currentPage = 1;
    this.loadProducts();
  }

  onColorChange(event: Event) {
    const checkbox = event.target as HTMLInputElement;
    if (checkbox.checked) {
      this.selectedColors.push(checkbox.value);
    } else {
      this.selectedColors = this.selectedColors.filter(
        (color) => color !== checkbox.value
      );
    }
    this.currentPage = 1;
    this.loadProducts();
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.loadProducts();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.loadProducts();
    }
  }
}
