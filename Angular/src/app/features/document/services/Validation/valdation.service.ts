import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Validationnn } from 'src/app/features/document/Model/ValidationMod';
@Injectable({
  providedIn: 'root'
})
export class ValdationService {
 private apiUrl = 'http://localhost:8088/validations';
  constructor(private http : HttpClient) { }
  getValidationByENCId(idEncadrant: number): Observable<Validationnn[]> {
    return this.http.get<Validationnn[]>(`${this.apiUrl}/encadrant/${idEncadrant}`)
  }
  getValidationByEtudiantId(idEtudiant: number): Observable<Validationnn[]> {
    return this.http.get<Validationnn[]>(`${this.apiUrl}/etudiant/${idEtudiant}`)
  }
  validateDocument(
    idDocument: number,
    idEncadrant: number,
    statut: string,
    commentaire: string,
    etudiantId: number,
    offreId: number
  ): Observable<string> {
    const params = new HttpParams()
      .set('idDocument', idDocument.toString())
      .set('idEncadrant', idEncadrant.toString())
      .set('statut', statut)
      .set('commentaire', commentaire)
      .set('etudiantId', etudiantId.toString())
      .set('offreId', offreId.toString());
  
    return this.http.post<string>(this.apiUrl, null, {
      params,
      responseType: 'text' as 'json'
    });
  }
  
  getValidations(): Observable<Validationnn[]> {
    return this.http.get<Validationnn[]>(this.apiUrl);
  }
  deleteValidation(idValidation: number): Observable<String> {
    return this.http.delete<String>(`${this.apiUrl}/${idValidation}`, { responseType: 'text' as 'json' });
  }
  updateValidation(idValidation: number, updatedValidation: Validationnn): Observable<string> {
    return this.http.put<string>(`${this.apiUrl}/${idValidation}`, updatedValidation , { responseType: 'text' as 'json' });
  }
  analyseCommentaire(idValidation: number) {
    return this.http.get(`${this.apiUrl}/analyse-commentaire/${idValidation}`, { responseType: 'text' });
  }
  analyserCommentaires(comments: string[]): Observable<{ positif: number; negatif: number }> {
    return this.http.post<{ positif: number; negatif: number }>(`${this.apiUrl}/analyse-tous-commentaires`, comments);
  }
  
}
