export interface ProductAttribute {
  id: number;
  productId: number;
  color: string;
  stock: number;
  size: number;
  price: number;
  imageUrl?: string[];
}
