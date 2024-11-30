import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../../interfaces/product';
import { Category } from '../../../interfaces/category';
import { ProductService } from '../../service/product.service';
import { CategorieService } from '../../../categories/service/categorie.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  newProductForm!: FormGroup;
  categories: Category[] = [];
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
      private productService: ProductService,
      private categorieService: CategorieService,
      private router: Router
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.loadCategories();
  }

  initForm(): void {
    this.newProductForm = new FormGroup({
      nameProd: new FormControl('', Validators.required),
      imageUrl: new FormControl('', Validators.required),
      price: new FormControl('', [Validators.required, Validators.min(0)]),
      rating: new FormControl('', [Validators.required, Validators.min(1), Validators.max(5)]), // Rating should be between 1 and 5
      categoryId: new FormControl('', Validators.required)
    });
  }

  loadCategories(): void {
    this.categorieService.getAllCategories().subscribe(
        (categories: Category[]) => {
          this.categories = categories;
        },
        (error) => {
          console.error('Error fetching categories:', error);
        }
    );
  }

  addProduct(): void {
    if (this.newProductForm.valid) {
      const newProduct: Product = {
        idProd: 0,
        nameProd: this.newProductForm.value.nameProd,
        imageUrl: this.newProductForm.value.imageUrl,
        price: this.newProductForm.value.price,
        rating: this.newProductForm.value.rating,
        date: new Date(),
        category: {
          idCat: this.newProductForm.value.categoryId,
          nameCat: '', // You might want to fetch the name from the categories array
          descriptionCat: '' // Same as above
        }
      };

      this.productService.addProduct(newProduct).subscribe(
          (response: Product) => {
            this.successMessage = 'The product has been added successfully!';
            this.errorMessage = null;
            this.newProductForm.reset();

            setTimeout(() => {
              this.router.navigate(['/products']);
            }, 3000);
          },
          (error) => {
            this.errorMessage = 'An error occurred while adding the product.';
            this.successMessage = null;
            console.error('Error adding product:', error);
          }
      );
    }
  }
}