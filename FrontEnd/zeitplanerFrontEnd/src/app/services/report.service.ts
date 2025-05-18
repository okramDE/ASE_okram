import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TimeUsageDto } from '../models/time-usage';

@Injectable({ providedIn: 'root' })
export class ReportService {
  private base = 'http://localhost:8080/api/report';

  constructor(private http: HttpClient) {}

  getTimeUsage(from: string, to: string): Observable<TimeUsageDto[]> {
    return this.http.get<TimeUsageDto[]>(
      `${this.base}/time-usage`,
      { params: { from, to } }
    );
  }
}
