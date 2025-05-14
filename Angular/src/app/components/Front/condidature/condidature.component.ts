import { Component, OnInit } from '@angular/core';
import { OffreStageService } from 'src/app/services/offre-stage.service';
import { Candidature } from 'src/app/models/candidature.model';
import * as bootstrap from 'bootstrap';
import { InterviewService } from 'src/app/services/interview.service';

@Component({
  selector: 'app-condidature',
  templateUrl: './condidature.component.html',
  styleUrls: ['./condidature.component.css']
})
export class CondidatureComponent implements OnInit {
  candidatures: Candidature[] = [];
  errorMessage: string = '';
  selectedCandidature: Candidature | null = null;
  newInterview: any = {
    dateEntretien: '',
    mode: '',
    lienVisio: '',
    note: null,
    commentaire: '',
    status: 'Prévue',
    candidature: null
  };

  constructor(private offreService: OffreStageService, private interviewService: InterviewService) {}

  ngOnInit(): void {
    this.loadCandidatures();
  }

  loadCandidatures(): void {
    const userId = 1; // Replace with the actual student ID
    this.offreService.getCandidaturesByUser(userId).subscribe(
      (data: Candidature[]) => {
        this.candidatures = data;
      },
      (error) => {
        this.errorMessage = 'Erreur lors de la récupération des candidatures';
        console.error(error);
      }
    );
  }

  updateStatus(candidature: Candidature, newStatus: string): void {
    this.offreService.updateCandidatureStatus(candidature.id, newStatus).subscribe(
      (updatedCandidature) => {
        candidature.etat = updatedCandidature.etat;
        alert('Statut mis à jour avec succès');
      },
      (error) => {
        console.error('Erreur lors de la mise à jour du statut', error);
      }
    );
  }

  deleteCandidature(candidature: Candidature): void {
    if (confirm('Voulez-vous vraiment supprimer cette candidature ?')) {
      this.offreService.deleteCandidature(candidature.id).subscribe(
        () => {
          this.candidatures = this.candidatures.filter(c => c.id !== candidature.id);
          alert('Candidature supprimée avec succès');
        },
        (error) => {
          console.error('Erreur lors de la suppression', error);
        }
      );
    }
  }

  openInterviewModal(candidature: Candidature) {
    this.selectedCandidature = candidature;
    this.newInterview = {
      dateEntretien: '',
      mode: '',
      lienVisio: '',
      note: null,
      commentaire: '',
      status: 'Prévue',
      candidature: candidature // Link to candidature
    };

    // Show the modal
    const modalElement = document.getElementById('interviewModal');
    if (modalElement) {
      const bootstrapModal = new bootstrap.Modal(modalElement);
      bootstrapModal.show();
    }
  }


  submitInterview() {
    if (!this.selectedCandidature) return;

    this.interviewService.ajouterInterview(this.newInterview).subscribe(() => {
      alert('Entretien ajouté avec succès');
      const modalElement = document.getElementById('interviewModal');
      if (modalElement) {
        const bootstrapModal = bootstrap.Modal.getInstance(modalElement);
        bootstrapModal?.hide();
      }
    });
  }


}
