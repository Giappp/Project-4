import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../../interfaces/product';
import { Category } from '../../../interfaces/category';
import { ProductService } from '../../service/product.service';
import { CategorieService } from '../../../categories/service/categorie.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  editProductForm!: FormGroup;
  categories: Category[] = [];
  productId: number = 0;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
      private productService: ProductService,
      private categorieService: CategorieService,
      private route: ActivatedRoute,
      private router: Router
  ) { }

  ngOnInit(): void {
    this.productId = this.route.snapshot.params['id'];
    this.initForm();
    this.loadCategories();
    this.loadProduct();
  }

  initForm(): void {
    this.editProductForm = new FormGroup({
      nameProd: new FormControl('', Validators.required),
      imageUrl: new FormControl('', Validators.required),
      price: new FormControl('', [Validators.required, Validators.min(0)]),
      rating: new FormControl('', [Validators.required, Validators.min(1), Validators.max(5)]), // Rating should be between 1 and 5
      categoryId: new FormControl('', Validators.required)
    });
  }

  loadCategories(): void {
    this.categorieService.getAllCategories().subscribe({
      next: (categories: Category[]) => {
        this.categories = categories;
      },
      error: (error) => {
        console.error('Error fetching categories:', error);
      }
    });
  }

  loadProduct(): void {
    this.productService.getProductById(this.productId).subscribe({
      next: (product: Product) => {
        this.editProductForm.patchValue({
          nameProd: product.nameProd,
          imageUrl: product.imageUrl,
          price: product.price,
          rating: product.rating,
          categoryId: product.category.idCat
        });
      },
      error: (error) => {
        console.error('Error fetching product:', error);
      }
    });
  }

  updateProduct(): void {
    if (this.editProductForm.valid) {
      const updatedProduct: Product = {
        idProd: this.productId,
        nameProd: this.editProductForm.value.nameProd,
        imageUrl: this.editProductForm.value.imageUrl,
        price: this.editProductForm.value.price,
        rating: this.editProductForm.value.rating,
        date: new Date(),
        category: {
          idCat: this.editProductForm.value.categoryId,
          nameCat: '', // You might want to fetch the name from the categories array if needed
          descriptionCat: '' // Same as above
        }
      };

      this.productService.updateProduct(updatedProduct).subscribe({
        next: (response: Product) => {
          this.successMessage = 'The product has been updated successfully!';
          this.errorMessage = null;
          setTimeout(() => {
            this.router.navigate(['/products']);
          }, 2000);
        },
        error: (error) => {
          this.errorMessage = 'An error occurred while updating the product.';
          this.successMessage = null;
          console.error('Error updating product:', error);
        }
      });
    }
  }

  cancelUpdate(): void {
    this.router.navigate(['/products']);
  }
}