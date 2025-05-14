import { Component,OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { DocumentService } from 'src/app/features/document/services/document.service';
import { Document } from 'src/app/features/document/Model/DocumentMod';
import { OffreStageService } from 'src/app/services/offre-stage.service';
import { OffreStage } from 'src/app/models/offre-stage.model';
import { Modal } from 'bootstrap';
import { Router } from '@angular/router';
declare var window: any;

@Component({
  selector: 'app-list-doc-f',
  templateUrl: './list-doc-f.component.html',
  styleUrls: ['./list-doc-f.component.css']
})
export class ListDocFComponent implements OnInit {
  userRole: string | null = null;
  userId : number | null = null;
  documents: Document[] = [];
  selectedCriteria: string = 'documentTitle';
  searchValue: string = '';
  sortedDocuments : Document[]=[];
  sortAsc = true;
  selectedOffre: OffreStage | null = null;
  offreModal: any;
  username : string = '';
  offreTitles: { [id: number]: string } = {};

  constructor(private router: Router, private authService: AuthService, private documentService: DocumentService, private OS : OffreStageService) {}

  ngOnInit(): void {
    this.userRole = this.authService.getUserRoleFromToken();
    this.userId = this.authService.getUserIdFromToken();
    console.log('✅ Rôle récupéré depuis le token :', this.userRole);
    console.log('✅ ID utilisateur récupéré depuis le token :', this.userId);

    if (this.userId) {
      this.documentService.getDocumentsByEtudiantId(this.userId).subscribe(docs => {
        this.documents = docs;
        this.loadOffreTitles();
      });
      this.authService.getUserNameById(this.userId).subscribe({
        next: (name) => {
          this.username = name;
        },
        error: () => {
          this.username = 'Utilisateur inconnu';
        }
      })
    }
  }

  loadOffreTitles(): void {
    this.documents.forEach(doc => {
      if (!this.offreTitles[doc.stageId]) {
        this.OS.getOffreById(doc.stageId).subscribe(offre => {
          this.offreTitles[doc.stageId] = offre.titre || 'Offre';
        });
      }
    });
  }

  voirOffre(offreId: number) {
    this.OS.getOffreById(offreId).subscribe({
      next: (offre) => {
        this.selectedOffre = offre;
        this.offreModal = new window.bootstrap.Modal(
          document.getElementById('offreModal')
        );
        this.offreModal.show();
      },
      error: (err) => {
        console.error('Erreur lors du chargement de l’offre', err);
      }
    });
  }

  sortDocuments(): void {
    this.sortAsc = !this.sortAsc;
    this.documents.sort((a, b) => {
      const dateA = new Date(a.dateUpload).getTime();
      const dateB = new Date(b.dateUpload).getTime();
      return this.sortAsc ? dateA - dateB : dateB - dateA;
    });
  }

  getFilteredDocuments() {
    return this.documents.filter(doc => {
      const value = this.searchValue.toLowerCase();
      if (this.selectedCriteria === 'studentId' && doc.etudiantId.toString().includes(value)) return true;
      if (this.selectedCriteria === 'documentTitle' && doc.titreDocument.toLowerCase().includes(value)) return true;
      if (this.selectedCriteria === 'statut' && doc.statutDocument.toLowerCase().includes(value)) return true;
      if(this.selectedCriteria === 'offreTitre' && this.offreTitles[doc.stageId]?.toLowerCase().includes(value)) return true;
      return false;
    });
  }

  deleteDOC(idDocument : number): void{
    if (confirm('Are you sure you want to delete this document?')) {
      this.documentService.delDocument(idDocument).subscribe({
        next: () => {
          this.documents = this.documents.filter(doc => doc.idDocument !== idDocument);
          alert('Document deleted successfully');
        },
        error: (err) => {
          console.error('Error deleting document:', err);
          alert('Failed to delete document');
        }
      });
    }
  }

  applyToOffer(stageId: number): void {
    if (this.userId) {
      this.router.navigate(['/document/add'], {
        queryParams: {
          userId: this.userId,
          stageId: stageId
        }
      });
    } else {
      alert('Vous devez être connecté pour postuler.');
    }
  }
}
