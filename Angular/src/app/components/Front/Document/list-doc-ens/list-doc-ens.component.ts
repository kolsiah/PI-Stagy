import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { DocumentService } from 'src/app/features/document/services/document.service';
import { Document } from 'src/app/features/document/Model/DocumentMod';
import { OffreStageService } from 'src/app/services/offre-stage.service';

@Component({
  selector: 'app-list-doc-ens',
  templateUrl: './list-doc-ens.component.html',
  styleUrls: ['./list-doc-ens.component.css']
})
export class ListDocEnsComponent implements OnInit {
  userRole: string | null = null;
  userId: number | null = null;
  ListDoc: Document[] = [];
  selectedCriteria: string = 'documentTitle';
  searchValue: string = '';
  sortedDocuments: Document[] = [];
  sortAsc = true;
  resume: string = '';
  technologies: string[] = [];
  activeResumeId: number | null = null;
  doc1Id: number | null = null;
  doc2Id: number | null = null;
  similarityScore: number | null = null;
  plagiarismSuspected: boolean = false;

  userNames: { [id: number]: string } = {};
  offreTitles: { [id: number]: string } = {};

  constructor(
    private authService: AuthService,
    private documentService: DocumentService,
    private offreService: OffreStageService
  ) {}

  ngOnInit(): void {
    this.userRole = this.authService.getUserRoleFromToken();
    this.userId = this.authService.getUserIdFromToken();
    this.documentService.getAllDocumentsForEnseignant().subscribe({
      next: (docs) => {
        this.ListDoc = docs;
        docs.forEach(doc => {
          this.getUserName(doc.etudiantId);
          this.getOffreTitle(doc.stageId);
        });
      },
      error: (err) => console.error(err)
    });
  }

  getUserName(userId: number): void {
    if (!this.userNames[userId]) {
      this.authService.getUserNameById(userId).subscribe({
        next: (name) => this.userNames[userId] = name,
        error: () => this.userNames[userId] = 'Utilisateur inconnu'
      });
    }
  }

  getOffreTitle(stageId: number): void {
    if (!this.offreTitles[stageId]) {
      this.offreService.getOffreById(stageId).subscribe({
        next: (offre) => this.offreTitles[stageId] = offre.titre,
        error: () => this.offreTitles[stageId] = 'Offre inconnue'
      });
    }
  }

  sortDocuments(): void {
    this.sortAsc = !this.sortAsc;
    this.ListDoc.sort((a, b) => {
      const dateA = new Date(a.dateUpload).getTime();
      const dateB = new Date(b.dateUpload).getTime();
      return this.sortAsc ? dateA - dateB : dateB - dateA;
    });
  }

  getFilteredDocuments() {
    return this.ListDoc
      .filter(doc => doc.statutDocument !== 'validé')
      .filter(doc => {
        const value = this.searchValue.toLowerCase();
        switch (this.selectedCriteria) {
          case 'studentId':
            return doc.etudiantId?.toString().includes(value);
          case 'documentTitle':
            return doc.titreDocument?.toLowerCase().includes(value);
          case 'statut':
            return doc.statutDocument?.toLowerCase().includes(value);
          default:
            return true;
        }
      });
  }

  deleteDOC(idDocument: number): void {
    if (confirm('Are you sure you want to delete this document?')) {
      this.documentService.delDocument(idDocument).subscribe({
        next: () => {
          this.ListDoc = this.ListDoc.filter(doc => doc.idDocument !== idDocument);
          alert('Document deleted successfully');
        },
        error: (err) => {
          console.error('Error deleting document:', err);
          alert('Failed to delete document');
        }
      });
    }
  }

  analyze(id: number): void {
    this.documentService.analyzeDocument(id).subscribe({
      next: res => {
        this.resume = res.resume;
        this.technologies = res.technologies;
        this.activeResumeId = id;
      },
      error: err => {
        console.error('Erreur analyse document :', err);
        alert("Échec de l'analyse du document.");
      }
    });
  }

  compareDocs(): void {
    if (this.doc1Id && this.doc2Id && this.doc1Id !== this.doc2Id) {
      this.documentService.compareDocuments(this.doc1Id, this.doc2Id).subscribe({
        next: (res) => {
          this.similarityScore = res.similarityScore;
          this.plagiarismSuspected = res.plagiarismSuspected;
        },
        error: (err) => {
          console.error('Erreur comparaison :', err);
          alert("Erreur lors de la comparaison des documents.");
        }
      });
    } else {
      alert("Veuillez sélectionner deux documents différents à comparer.");
    }
  }
}
