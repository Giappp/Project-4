// product.service.spec.ts
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProductService } from './product.service';
import { Category } from '../../model/category';

describe('ProductService', () => {
  let service: ProductService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProductService],
    });
    service = TestBed.inject(ProductService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Ensure that no unmatched requests are outstanding
  });

  it('should get all product categories', () => {
    const mockCategories: Category[] = [
      { id: 1, categoryName: 'Electronics', productCategory: 'cs', genders: [] },
      { id: 2, categoryName: 'Books', productCategory: 'haha', genders: [] },
      // productCategory: string;
      // genders?: Gender[];
    ];

    service.getAllProductsCategories().subscribe(categories => {
      expect(categories).toEqual(mockCategories);
    });

    const req = httpMock.expectOne('/api/products/genders');
    expect(req.request.method).toBe('GET');
    req.flush(mockCategories); // Respond with mock data
  });

  // Add more tests as needed...
});
