import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AufgabeDto } from '../models/aufgabe';

@Injectable({ providedIn: 'root' })
export class AufgabeService {
  private base = 'http://localhost:8080/api/aufgaben';

  constructor(private http: HttpClient) {}

  getAll(): Observable<AufgabeDto[]> {
    return this.http.get<AufgabeDto[]>(this.base);
  }

  getById(id: number): Observable<AufgabeDto> {
    return this.http.get<AufgabeDto>(`${this.base}/${id}`);
  }

  create(dto: AufgabeDto): Observable<AufgabeDto> {
    return this.http.post<AufgabeDto>(this.base, dto);
  }

  update(id: number, dto: AufgabeDto): Observable<AufgabeDto> {
    return this.http.put<AufgabeDto>(`${this.base}/${id}`, dto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }

  searchByDeadline(von: string, bis: string): Observable<AufgabeDto[]> {
    return this.http.get<AufgabeDto[]>(`${this.base}/suche`, {
      params: { von, bis }
    });
  }
}
