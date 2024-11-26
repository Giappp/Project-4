import { Gender } from './gender';

export interface Category {
  id: number;
  categoryName: string;
  productCategory: string;
  genders?: Gender[];
}
