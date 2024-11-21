export interface ProductFilter {
  minPrice?: number;
  maxPrice?: number;
  sizes?: string[];
  colors?: string[];
  type?: string;
  page: number;
  pageSize: number;
}
