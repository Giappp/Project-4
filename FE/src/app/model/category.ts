import { Gender } from './gender';

export interface Category {
  id: number;
  categoryName: string;
  loai: string;
  genders?: Gender[];
}
