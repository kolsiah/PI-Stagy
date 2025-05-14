import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OffreStage } from 'src/app/models/offre-stage.model';
import { OffreStageService } from 'src/app/services/offre-stage.service';


@Component({
  selector: 'app-offre-details',
  templateUrl: './offre-details.component.html',
  styleUrls: ['./offre-details.component.css']
})
export class OffreDetailsComponent implements OnInit {
  offre!: OffreStage; // Stocke l'offre récupérée
  id!: number; // Stocke l'ID de l'offre
  nomEntreprise: string = ''; // Stocke le nom de l'entreprise

  constructor(
    private route: ActivatedRoute, // Pour récupérer l'ID depuis l'URL
    private router: Router, // Pour naviguer après suppression/modification
    private offreService: OffreStageService // Service pour récupérer l'offre
  ) {}

  ngOnInit(): void {
    // Récupérer l'ID de l'offre depuis l'URL
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    if (this.id) {
      // Appel au service pour récupérer les détails de l'offre
      this.offreService.getOffreDetailsWithEntreprise(this.id).subscribe(
        (data: OffreStage) => {
          this.offre = data;

          // Récupérer le nom de l'entreprise en utilisant idEntreprise
          if (this.offre.idEntreprise) {
            this.offreService.getEntreprises().subscribe(
              (entreprises) => {
                const entreprise = entreprises.find(e => e.idEntreprise === this.offre.idEntreprise);
                this.nomEntreprise = entreprise ? entreprise.nomEntreprise : 'Entreprise inconnue';
              },
              (error) => {
                console.error('Erreur lors du chargement des entreprises', error);
              }
            );
          }
        },
        (error) => {
          console.error('Erreur lors du chargement de l\'offre', error);
        }
      );
    }
  }

  // Méthode pour modifier une offre
  modifierOffre() {
    this.router.navigate(['/offres/edit', this.id]); // Rediriger vers la page d'édition
  }

  // Méthode pour supprimer une offre
  supprimerOffre() {
    if (confirm('Voulez-vous vraiment supprimer cette offre ?')) {
      this.offreService.deleteOffre(this.id).subscribe(() => {
        alert('Offre supprimée avec succès');
        this.router.navigate(['/offres']); // Rediriger vers la liste des offres
      }, error => {
        console.error('Erreur lors de la suppression', error);
      });
    }
  }
}

