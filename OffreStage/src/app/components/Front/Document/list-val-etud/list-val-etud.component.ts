import { Component,OnInit } from '@angular/core';
import { Validationnn } from 'src/app/features/document/Model/ValidationMod';
import { ValdationService } from 'src/app/features/document/services/Validation/valdation.service';
import { AuthService } from 'src/app/services/auth.service';
import { DocumentService } from 'src/app/features/document/services/document.service';
import { OffreStageService } from 'src/app/services/offre-stage.service';
@Component({
  selector: 'app-list-val-etud',
  templateUrl: './list-val-etud.component.html',
  styleUrls: ['./list-val-etud.component.css']
})
export class ListValEtudComponent implements OnInit {
  ListVal: Validationnn[] = [];
  userId: number | null = null;
  userRole: string | null = null;
  modal: any;
  selectedComment: string = '';
  userNames: { [id: number]: string } = {};
  searchValue: string = '';
  selectedCriteria: string = 'etudiantId';
  sortAsc: boolean = true;
  documentTitles: { [id: number]: string } = {};
  offreTitles: { [id: number]: string } = {};
  constructor(private validationService: ValdationService, private authService: AuthService,   private docService: DocumentService,  private offreService: OffreStageService) {}

  ngOnInit(): void {
    this.userRole = this.authService.getUserRoleFromToken();
    this.userId = this.authService.getUserIdFromToken();
    this.loadValidations();
  }
  getFilteredValidations() {
    return this.ListVal
      .filter(val => {
        const value = this.searchValue.toLowerCase();
        switch (this.selectedCriteria) {
          case 'etudiantId':
            return val.etudiantId?.toString().includes(value);
          case 'offreId':
            return val.offreId?.toString().includes(value);
          case 'idDocument':
            return val.idDocument?.toString().includes(value);
          case 'statut':
            return val.statut?.toLowerCase().includes(value);
            case 'offreTitre':
              const titre = this.offreTitles[val.offreId]?.toLowerCase() || '';
              return titre.includes(value);  
          default:
            return true;
        }
      });
  }
  
  loadDocumentTitles(): void {
    this.ListVal.forEach(val => {
      if (!this.documentTitles[val.idDocument]) {
        this.docService.getDocumentById(val.idDocument).subscribe(doc => {
          this.documentTitles[val.idDocument] = doc.titreDocument;
        });
      }
    });
  }
  // ↕️ Tri par date
  sortValidations(): void {
    this.sortAsc = !this.sortAsc;
    this.ListVal.sort((a, b) => {
      const dateA = new Date(a.dateValidation).getTime();
      const dateB = new Date(b.dateValidation).getTime();
      return this.sortAsc ? dateA - dateB : dateB - dateA;
    });
  }
  
  openCommentModal(comment: string): void {
    this.selectedComment = comment;
    const modalEl = document.getElementById('commentModal');
    if (modalEl) {
      const bootstrapModal = new window.bootstrap.Modal(modalEl);
      bootstrapModal.show();
    }
  }
  getUserName(userId: number): void {
    if (!this.userNames[userId]) {
      this.authService.getUserNameById(userId).subscribe({
        next: (name) => {
          this.userNames[userId] = name;
        },
        error: () => {
          this.userNames[userId] = 'Utilisateur inconnu';
        }
      });
    }
  }loadValidations() {
    this.validationService.getValidationByEtudiantId(this.userId!).subscribe({
      next: (data) => {
        this.ListVal = data;
  
        // Charger les noms d'utilisateurs
        data.forEach(val => {
          this.getUserName(val.etudiantId);
          this.getUserName(val.idEncadrant);
        });
  
        // Charger les titres des documents et offres
        this.loadDocumentTitles();
        this.loadOffreTitles();
      },
      error: (err) => {
        console.error("Erreur lors du chargement des validations", err);
      }
    });
  }
  
  loadOffreTitles(): void {
    this.ListVal.forEach(val => {
      if (!this.offreTitles[val.offreId]) {
        this.offreService.getOffreById(val.offreId).subscribe(offre => {
          this.offreTitles[val.offreId] = offre.titre || 'Offre';
        });
      }
    });
  }
  
}
