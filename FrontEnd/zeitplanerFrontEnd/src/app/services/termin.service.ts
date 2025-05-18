import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TerminDto } from '../models/termin';

@Injectable({ providedIn: 'root' })
export class TerminService {
  private base = 'http://localhost:8080/api/termine';

  constructor(private http: HttpClient) {}

  getAll(): Observable<TerminDto[]> {
    return this.http.get<TerminDto[]>(this.base);
  }

  getById(id: number): Observable<TerminDto> {
    return this.http.get<TerminDto>(`${this.base}/${id}`);
  }

  create(dto: TerminDto): Observable<TerminDto[]> {
    return this.http.post<TerminDto[]>(this.base, dto);
  }

  update(id: number, dto: TerminDto): Observable<TerminDto> {
    return this.http.put<TerminDto>(`${this.base}/${id}`, dto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }

  // Suche nach Kategorie
  getByKategorie(kategorieId: number): Observable<TerminDto[]> {
    return this.http.get<TerminDto[]>(`${this.base}/kategorie/${kategorieId}`);
  }

  // Suche nach Zeitraum
  search(von: string, bis: string): Observable<TerminDto[]> {
    return this.http.get<TerminDto[]>(`${this.base}/suche`, {
      params: { von, bis }
    });
  }
}
