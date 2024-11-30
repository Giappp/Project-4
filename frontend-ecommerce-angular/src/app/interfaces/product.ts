import { Category } from "./category";

export interface Product {
  idProd: number;
  nameProd: string;
  price: number;
  rating: number;
  date: Date;
  imageUrl: string;
  category: Category;
}
