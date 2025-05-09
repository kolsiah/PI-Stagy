import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { OffreStage } from '../models/offre-stage.model';
import { Candidature } from '../models/candidature.model';

// Assure-toi que tu as l'URL de ton API dans environment

@Injectable({
  providedIn: 'root'
})
export class OffreStageService {
  private apiUrl = '/offres';
  private candidatureApiUrl = '/candidatures';

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private http: HttpClient) {}

  // Méthode pour récupérer toutes les offres
  getAllOffres(): Observable<OffreStage[]> {
    return this.http.get<OffreStage[]>(this.apiUrl);
  }

  // Méthode pour ajouter une nouvelle offre
  ajouterOffre(offreStage: OffreStage): Observable<OffreStage> {
    return this.http.post<OffreStage>(this.apiUrl, offreStage);
  }

  // Méthode pour récupérer une offre par ID
  getOffreById(id: number): Observable<OffreStage> {
    return this.http.get<OffreStage>(`${this.apiUrl}/${id}`);
  }

  // Méthode pour mettre à jour une offre
  updateOffre(id: number, updatedOffre: OffreStage): Observable<OffreStage> {
    return this.http.put<OffreStage>(`${this.apiUrl}/${id}`, updatedOffre);
  }

  // Méthode pour supprimer une offre
  deleteOffre(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  getsearch(param: any): Observable<OffreStage[]> {
    return this.http.get<OffreStage[]>(this.apiUrl + "/search/" + param);
  }

  getOffreValider(): Observable<OffreStage[]> {
    return this.http.get<OffreStage[]>(this.apiUrl + "/offreValider");
  }

  postCandidature(candidature: Candidature): Observable<Candidature> {
    return this.http.post<Candidature>(this.candidatureApiUrl, candidature).pipe(
      catchError((error) => {
        console.error('Erreur lors de l\'envoi de la candidature:', error);
        throw error; // Re-throw the error after logging it
      })
    );
  }

  getCandidaturesByUser(userId: number): Observable<Candidature[]> {
    return this.http.get<Candidature[]>(`${this.candidatureApiUrl}/user/${userId}`).pipe(
      catchError((error) => {
        console.error('Error fetching candidatures for user:', error);
        return of([]); // Return an empty array as a fallback
      })
    );
  }

  // Update the status of a candidature
  updateCandidatureStatus(id: number, etat: string): Observable<Candidature> {
    return this.http.put<Candidature>(`${this.candidatureApiUrl}/${id}`, { etat });
  }

  // Delete a candidature
  deleteCandidature(id: number): Observable<any> {
    return this.http.delete<any>(`${this.candidatureApiUrl}/${id}`);
  }

  // Nouvelle méthode pour récupérer une offre avec les détails de l'entreprise
  getOffreDetailsWithEntreprise(id: number): Observable<OffreStage> {
    return this.http.get<OffreStage>(`${this.apiUrl}/details/${id}`);
  }

  // Méthode pour récupérer la liste des entreprises
  getEntreprises(): Observable<any[]> {
    return this.http.get<any[]>(`/entreprises/retrieveAllEntreprises`);
  }
}
