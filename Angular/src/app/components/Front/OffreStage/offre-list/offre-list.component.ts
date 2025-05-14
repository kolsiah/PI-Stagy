import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { OffreStage } from 'src/app/models/offre-stage.model';
import { OffreStageService } from 'src/app/services/offre-stage.service';
import * as bootstrap from 'bootstrap';
@Component({
  selector: 'app-offre-list',
  templateUrl: './offre-list.component.html',
  styleUrls: ['./offre-list.component.css']
})
export class OffreListComponent implements OnInit {
  offres: OffreStage[] = [];
  page: number = 1;
  totalPages: number = 1;
  pagination: number[] = [];
  errorMessage: string = '';
  selectedOffre: any = null;
  searchControl: FormControl = new FormControl();

  constructor(private router: Router, private offreService: OffreStageService) {}

  ngOnInit(): void {
    console.log('OffreListComponent initialized'); // Debug log
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
              console.log('Search results:', data); // Debug log
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
    console.log('Loading offers...'); // Debug log
    this.offreService.getAllOffres().subscribe(
      (response: OffreStage[]) => {
        console.log('Offers loaded:', response); // Debug log
        this.offres = response;
        this.totalPages = 1; // Default value if totalPages is not provided
        this.pagination = Array.from({ length: this.totalPages }, (_, i) => i + 1);
      },
      (error) => {
        this.errorMessage = 'Erreur lors de la récupération des offres';
        console.error(error);
      }
    );
  }

  changePage(newPage: number): void {
    this.page = newPage;
    this.loadOffres();
  }

  modifierOffre(id: any) {
    this.router.navigate(['/edit-offre/', id]);
  }

  supprimerOffre(id: any) {
    if (confirm('Voulez-vous vraiment supprimer cette offre ?')) {
      this.offreService.deleteOffre(id).subscribe(() => {
        alert('Offre supprimée avec succès');
        this.loadOffres();
      }, error => {
        console.error('Erreur lors de la suppression', error);
      });
    }
  }

  ouvrirDetails(offre: any): void {
    this.selectedOffre = offre;
    const modalElement = document.getElementById('offreDetailModal');
    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
    }
  }
}
