import { Component, Injectable, Input, OnInit } from '@angular/core';
import { Product } from '../products/models/product';

@Component({
  selector: 'product-item',
  templateUrl: './items.component.html',
})
export class ItemComponent implements OnInit {
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  @Input({ required: true }) item!: Product;
}
