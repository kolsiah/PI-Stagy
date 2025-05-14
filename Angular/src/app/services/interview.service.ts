import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InterviewService {

  private apiUrl = 'http://localhost:8080/interview';
  constructor(private http: HttpClient) {}

    ajouterInterview(interview: any): Observable<any> {
      return this.http.post<any>(this.apiUrl, interview);
    }

    afficherInterview(): Observable<any[]> {
      return this.http.get<any[]>(this.apiUrl);
    }
}
