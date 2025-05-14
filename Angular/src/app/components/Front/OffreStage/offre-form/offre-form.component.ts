import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OffreStageService } from 'src/app/services/offre-stage.service';
import { OffreStage } from 'src/app/models/offre-stage.model';

@Component({
  selector: 'app-offre-form',
  templateUrl: './offre-form.component.html',
  styleUrls: ['./offre-form.component.css']
})
export class OffreFormComponent implements OnInit {
  offreForm!: FormGroup; // Formulaire réactif
  offreId!: number | null; // ID de l'offre à modifier (si applicable)
  isEditMode: boolean = false; // Détecter si on est en mode modification
  entreprises: any[] = []; // Array to hold the list of enterprises

  constructor(
    private fb: FormBuilder, // FormBuilder pour créer des formulaires réactifs
    private offreService: OffreStageService, // Service pour interagir avec l'API
    private route: ActivatedRoute, // Pour récupérer l'ID depuis l'URL (si modification)
    private router: Router // Pour rediriger après une action
  ) {}

  ngOnInit(): void {
    // Initialisation du formulaire
    this.initForm();

    // Vérifier si nous modifions une offre existante
    this.offreId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.offreId) {
      // Si un ID est présent, nous sommes en mode édition
      this.isEditMode = true;
      this.loadOffre();
    }

    // Load the list of enterprises
    this.loadEntreprises();
  }

  // Initialisation du formulaire avec les valeurs par défaut
  initForm(): void {
    // Remove idEntreprise from form controls as it is derived from entreprise
    this.offreForm = this.fb.group({
      titre: ['', Validators.required],
      description: ['', Validators.required],
      entreprise: ['', Validators.required], // Bind the selected enterprise's ID
      type: ['', Validators.required],
      datePublication: ['', Validators.required],
      debutExpiration: ['', Validators.required],
      etat: [true, Validators.required]
    });
  }

  // Charger une offre pour la modification
  loadOffre(): void {
    this.offreService.getOffreById(this.offreId!).subscribe(
      (data: OffreStage) => {
        this.offreForm.patchValue({
          titre: data.titre,
          description: data.description,
          entreprise: data.entreprise,
          type: data.type,
          datePublication: data.datePublication,
          debutExpiration: data.debutExpiration,
          etat: data.etat
        });
      },
      (error) => {
        console.error('Erreur lors du chargement de l\'offre', error);
      }
    );
  }

  // Charger la liste des entreprises
  loadEntreprises(): void {
    this.offreService.getEntreprises().subscribe(
      (data: any[]) => {
        console.log('Fetched enterprises:', data); // Debugging log
        this.entreprises = data;
      },
      (error) => {
        console.error('Erreur lors du chargement des entreprises', error);
      }
    );
  }

  // Soumettre le formulaire (créer ou mettre à jour une offre)
  onSubmit(): void {
    console.log('Form Validity:', this.offreForm.valid);
    console.log('Form Value:', this.offreForm.value);

    if (this.offreForm.valid) {
      const formData: OffreStage = this.offreForm.value;

      // Explicitly set idEntreprise based on the selected enterprise
      formData.idEntreprise = this.offreForm.get('entreprise')?.value.id; // Extract the ID from the selected enterprise object
      formData.entreprise = this.offreForm.get('entreprise')?.value; // Keep the full enterprise object for backend compatibility

      console.log('Payload being sent to backend:', formData);

      if (this.isEditMode) {
        // Update an existing offer
        this.offreService.updateOffre(this.offreId!, formData).subscribe(
          (data) => {
            this.router.navigate(['/offres']); // Redirect after update
          },
          (error) => {
            console.error('Erreur lors de la mise à jour de l\'offre', error);
          }
        );
      } else {
        // Create a new offer
        this.offreService.ajouterOffre(formData).subscribe(
          (data) => {
            this.router.navigate(['/offres']); // Redirect after creation
          },
          (error) => {
            console.error('Erreur lors de l\'ajout de l\'offre', error);
          }
        );
      }
    }
  }
}
