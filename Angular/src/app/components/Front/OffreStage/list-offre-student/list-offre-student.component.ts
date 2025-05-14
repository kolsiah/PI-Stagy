import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { OffreStage } from 'src/app/models/offre-stage.model';
import { OffreStageService } from 'src/app/services/offre-stage.service';
import { Candidature } from 'src/app/models/candidature.model';
import * as bootstrap from 'bootstrap';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-list-offre-student',
  templateUrl: './list-offre-student.component.html',
  styleUrls: ['./list-offre-student.component.css']
})
export class ListOffreStudentComponent implements OnInit {
  offres: OffreStage[] = [];
  page: number = 1;
  errorMessage: string = '';
  selectedOffre: OffreStage | null = null;
  searchControl: FormControl = new FormControl();
  cvFile: File | null = null;
  motivationFile: File | null = null;
  userId : number | null = null;

  constructor(private router: Router, private offreService: OffreStageService, private authService : AuthService) {}

  ngOnInit(): void {
    this.userId = this.authService.getUserIdFromToken();
    console.log('✅ ID utilisateur récupéré depuis le token :', this.userId);
    this.loadOffres();

    this.searchControl.valueChanges
      .pipe(
        debounceTime(300),
        distinctUntilChanged()
      )
      .subscribe((term: string) => {
        if (term && term.trim() !== '') {
          this.offreService.getsearch(term).subscribe(
            (data: OffreStage[]) => {
              this.offres = data;
            },
            (error) => {
              this.errorMessage = 'Erreur lors de la recherche des offres';
              console.error(error);
            }
          );
        } else {
          this.loadOffres();
        }
      });
  }

  loadOffres(): void {
    this.offreService.getOffreValider().subscribe(
      (data: OffreStage[]) => {
        this.offres = data;
      },
      (error) => {
        this.errorMessage = 'Erreur lors de la récupération des offres';
        console.error(error);
      }
    );
  }

  loadCandidatures(userId: number): void {
    this.offreService.getCandidaturesByUser(userId).subscribe(
      (data: Candidature[]) => {
        if (data.length === 0) {
          this.errorMessage = 'Aucune candidature trouvée ou service indisponible.';
        } else {
          console.log('Candidatures:', data);
        }
      },
      (error) => {
        this.errorMessage = 'Erreur lors de la récupération des candidatures.';
        console.error(error);
      }
    );
  }

  Apply(offre: OffreStage): void {
    this.selectedOffre = offre;
    const modalElement = document.getElementById('applyModal');
    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
    } else {
      console.error('Modal element not found');
    }
  }

  onFileSelected(event: Event, type: 'cv' | 'motivation'): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      if (type === 'cv') {
        this.cvFile = input.files[0];
        console.log('CV File selected:', this.cvFile);
      } else if (type === 'motivation') {
        this.motivationFile = input.files[0];
        console.log('Motivation File selected:', this.motivationFile);
      }
    }
  }

  submitApplication(): void {
    if (!this.selectedOffre || !this.cvFile || !this.motivationFile) {
      alert('Veuillez remplir tous les champs.');
      return;
    }

    const candidature: Candidature = {
      id: 0, // Placeholder, backend will generate the actual ID
      userId: 1, // Replace with the actual student ID
      offreStage: this.selectedOffre,
      etat: 'En attente', // Default status
      cvPath: this.cvFile.name,
      lettreMotivationPath: this.motivationFile.name,
      datePostulation: new Date().toISOString() // Current date in ISO format
    };

    this.offreService.postCandidature(candidature).subscribe(
      (response) => {
        console.log('Candidature envoyée avec succès:', response);
        alert('Candidature envoyée avec succès !');
        this.cvFile = null;
        this.motivationFile = null;
        const modalElement = document.getElementById('applyModal');
        if (modalElement) {
          const modal = bootstrap.Modal.getInstance(modalElement);
          modal?.hide();
        }
      },
      (error) => {
        console.error('Erreur lors de l\'envoi de la candidature', error);
        alert('Erreur lors de l\'envoi de la candidature.');
      }
    );
  }

  ouvrirDetails(offre: OffreStage): void {
    this.selectedOffre = offre;
    const modalElement = document.getElementById('offreDetailModal');
    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
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
