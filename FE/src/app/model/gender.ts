import { Category } from './category';

export interface Gender {
  id: number;
  name: string;
  categories?: Category[];
}
