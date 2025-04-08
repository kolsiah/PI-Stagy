import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class ValdationService {
 private apiUrl = 'http://localhost:8084/validations';
  constructor(private http : HttpClient) { }
  validateDocument(idDocument: number, idEncadrant: number, statut: string, commentaire: string): Observable<string> {
    const params = new HttpParams()
    .set('idDocument', idDocument.toString())
    .set('idEncadrant', idEncadrant.toString())
    .set('statut', statut)
    .set('commentaire', commentaire);

    // Sending POST request with parameters
    return this.http.post<string>(this.apiUrl, null, { params,responseType:'text' as 'json' });
  }

}
