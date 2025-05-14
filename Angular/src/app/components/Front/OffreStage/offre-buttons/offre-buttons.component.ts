import { Component, Input, Output, EventEmitter } from '@angular/core';
import { OffreStage } from 'src/app/models/offre-stage.model';

@Component({
  selector: 'app-offre-buttons',
  templateUrl: './offre-buttons.component.html',
  styleUrls: ['./offre-buttons.component.css']
})
export class OffreButtonsComponent {
  @Input() offre!: OffreStage; // Reçoit l'offre en entrée pour afficher les boutons
  @Output() onEdit = new EventEmitter<OffreStage>(); // Emmetteur pour l'édition de l'offre
  @Output() onDelete = new EventEmitter<OffreStage>(); // Emmetteur pour la suppression de l'offre

  constructor() { }

  // Méthodes pour émettre des événements lorsque l'utilisateur clique sur les boutons
  editOffre(): void {
    this.onEdit.emit(this.offre);
  }

  deleteOffre(): void {
    this.onDelete.emit(this.offre);
  }
}
