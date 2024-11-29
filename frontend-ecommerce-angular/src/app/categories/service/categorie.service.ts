import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../../config/config';
import { HttpClient } from '@angular/common/http';
import { Category } from '../../interfaces/category';

@Injectable({
  providedIn: 'root'
})
export class CategorieService {

  constructor(private http: HttpClient) { }

  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${API_BASE_URL}/categories`);
  }

  addCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(`${API_BASE_URL}/categories/create`, category);
  }

  deleteCategory(id: number): Observable<Category> {
    return this.http.delete<Category>(`${API_BASE_URL}/categories/${id}`);
  }

  getCategoryById(id: number): Observable<Category> {
    return this.http.get<Category>(`${API_BASE_URL}/categories/${id}`);
  }

  updateCategory(category: Category): Observable<Category> {
    return this.http.put<Category>(`${API_BASE_URL}/categories/${category.idCat}`, category);
  }
}
