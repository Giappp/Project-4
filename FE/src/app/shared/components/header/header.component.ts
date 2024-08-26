import { Component, EventEmitter, Input, input, Output } from '@angular/core';
import { ProductServiceService } from '../../../products/services/product-service.service';
import { Observable } from 'rxjs';
import { Product } from '../../../products/models/product';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  @Input() items: string[] = [];
  @Output() selectItem = new EventEmitter<string>();

  searchText = '';
  product!: any;
  product$!: Observable<Product[]>;
  products!: Product[];
  productNames: String[] = [];
  check!: string;

  constructor(private productService: ProductServiceService, private router: Router) {
    this.product$ = productService.getAllProducts();

    this.product$.subscribe(val => {
      this.products = val
      this.products.forEach(val => {
        this.productNames.push(val.name);
      })
    })
  }
  checkInput($event: any) {
    this.check = $event.target.value;
    console.log(this.check);
  }
  selectedItem(item: string) {
    console.log(item);
    this.product = this.products.find(
      val => this.nomallizeString(val.name) === this.nomallizeString(item)
    );
    console.log(this.product);
    this.productNames = [];
    this.searchText = '';
  }

  private nomallizeString(args: string): string {
    return args.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLocaleLowerCase();
  }

  navigateRegis() {
    this.router.navigate(['/auth/register'])
  }
}
