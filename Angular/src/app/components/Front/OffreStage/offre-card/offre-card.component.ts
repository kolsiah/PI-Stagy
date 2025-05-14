import { Component, Input } from '@angular/core';
import { OffreStage } from 'src/app/models/offre-stage.model';

@Component({
  selector: 'app-offre-card',
  templateUrl: './offre-card.component.html',
  styleUrls: ['./offre-card.component.css']
})
export class OffreCardComponent {
  @Input() offre!: OffreStage; // Propriété pour recevoir une offre en entrée

  constructor() { }

  // Optionnel: méthode pour afficher un bouton de redirection vers les détails
  goToDetails(id: number): void {
    console.log('Naviguer vers les détails de l\'offre:', id);
    // Ajouter la logique de redirection ici, si nécessaire
  }
}
