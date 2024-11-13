import { ProductAttribute } from './product-attribute';

export interface Product {
  id: number;
  name: string;
  description: string;
  sex: string;
  price: number;
  attributes?: ProductAttribute[];
}
