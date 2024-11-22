export interface Product {
  id: string;
  name: string;
  imageUrl: string;
  minPrice: number;
  maxPrice: number;
  sizes: string[];
  colors: string[];
  type: string;
}
