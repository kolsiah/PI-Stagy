import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Document } from '../Model/DocumentMod';
@Injectable({
  providedIn: 'root'
})
export class DocumentService {
private apiURL = 'http://localhost:8087/documents';
  constructor(private http : HttpClient) { }
  uploadDocument(formData: FormData): Observable<string> {
    return this.http.post<string>(this.apiURL+"/upload", formData,{ responseType: 'text' as 'json' });
  }
  getDocument():Observable<Document[]>{
    return this.http.get<Document[]>(this.apiURL);
  }
  delDocument(idDocument : number):Observable<void>{
    return this.http.delete<void>(`${this.apiURL}/${idDocument}`);
}
  getDocumentById(idDocument : number):Observable<Document>{
    return this.http.get<Document>(`${this.apiURL}/${idDocument}`);
  }
  updateDocument(document : Document):Observable<Document>{
    return this.http.put<Document>(`${this.apiURL}/${document.idDocument}`,document);
  }
  // /api/documents/analyze-report/{id}
analyzeDocument(id: number): Observable<{ resume: string, technologies: string[] }> {
  return this.http.get<{ resume: string, technologies: string[] }>(`${this.apiURL}/analyze-report/${id}`);
}

// /api/documents/compare/{id1}/{id2}
compareDocuments(id1: number, id2: number): Observable<{ similarityScore: number, plagiarismSuspected: boolean }> {
  return this.http.get<{ similarityScore: number, plagiarismSuspected: boolean }>(`${this.apiURL}/compare/${id1}/${id2}`);
}
getDocumentsByEtudiantId(id: number): Observable<Document[]> {
  return this.http.get<Document[]>(`${this.apiURL}/etudiant/${id}`);
}
getAllDocumentsForEnseignant(): Observable<Document[]> {
  return this.http.get<Document[]>(`${this.apiURL}/enseignant/documents`);
}

}
