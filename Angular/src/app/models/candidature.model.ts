import { OffreStage } from './offre-stage.model';

export interface Candidature {
  id: number; // Make id optional
  userId: number; 
  offreStage: OffreStage;
  cvPath: string;
  lettreMotivationPath: string;
  etat: string; 
  datePostulation: string; // Date of application
}