import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { ProductFilter } from './product-filter';
import { ProductService } from './product.service';
import { FilterOptions } from './filter-options';
import { Product } from './product.interface';
@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
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
      filter.productSizes = this.selectedSizes;
    }

    if (this.selectedColors.length) {
      filter.productColors = this.selectedColors;
    }

    this.productService.getProducts(filter).subscribe((response) => {
      console.log(response);
      this.products = response.data;
      this.totalItems = response.totalElements;
      this.totalPages = response.totalPages;
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
