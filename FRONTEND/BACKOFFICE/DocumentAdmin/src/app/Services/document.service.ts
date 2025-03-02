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
}
