import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { KategorieDto } from '../models/kategorie';

@Injectable({ providedIn: 'root' })
export class KategorieService {
  private base = 'http://localhost:8080/api/kategorien';

  constructor(private http: HttpClient) {}

  getAll(): Observable<KategorieDto[]> {
    return this.http.get<KategorieDto[]>(this.base);
  }

  getById(id: number): Observable<KategorieDto> {
    return this.http.get<KategorieDto>(`${this.base}/${id}`);
  }

  create(dto: KategorieDto): Observable<KategorieDto> {
    return this.http.post<KategorieDto>(this.base, dto);
  }

  update(id: number, dto: KategorieDto): Observable<KategorieDto> {
    return this.http.put<KategorieDto>(`${this.base}/${id}`, dto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
