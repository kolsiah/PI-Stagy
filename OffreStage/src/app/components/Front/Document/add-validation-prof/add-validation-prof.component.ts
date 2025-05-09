import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ValdationService } from 'src/app/features/document/services/Validation/valdation.service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-validation-prof',
  templateUrl: './add-validation-prof.component.html',
  styleUrls: ['./add-validation-prof.component.css']
})
export class AddValidationProfComponent implements OnInit {
  validationForm!: FormGroup;
  idDocument!: number;
  idEtudiant!: number;
  idOffre!: number;
  idEncadrant!: number;
  responseMessage = '';
  statutOptions = ['validé', 'rejeté', 'en_attente'];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private validationService: ValdationService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // 🔄 Récupération des infos depuis la carte document (via queryParams)
    this.route.queryParams.subscribe(params => {
      this.idDocument = +params['idDocument'];
      this.idEtudiant = +params['idEtudiant'];
      this.idOffre = +params['idOffre'];
    });

    this.idEncadrant = this.authService.getUserIdFromToken()!;

    // ✅ Initialisation du formulaire (uniquement commentaire + statut)
    this.validationForm = this.fb.group({
      statut: ['validé', Validators.required],
      commentaire: ['', Validators.required],
      idEncadrant: [{ value: this.idEncadrant, disabled: true }, Validators.required],
      etudiantId: [{ value: this.idEtudiant, disabled: true }, Validators.required],
      offreId: [{ value: this.idOffre, disabled: true }, Validators.required],
    });
  }

  onSubmit(): void {
    if (this.validationForm.valid) {
      const { statut, commentaire } = this.validationForm.value;
  
      this.validationService.validateDocument(
        this.idDocument,
        this.idEncadrant,
        statut,
        commentaire,
        this.idEtudiant,
        this.idOffre
      ).subscribe({
        next: (res) => {
          this.responseMessage = '✅ Validation ajoutée avec succès.';
          // ⏳ Petite pause optionnelle avant redirection
          setTimeout(() => {
            this.router.navigate(['/validation/list']);
          }, 1000); // 1 seconde avant redirection
        },
        error: (err) => {
          this.responseMessage = `❌ Erreur : ${err.message}`;
        }
      });
    } else {
      this.responseMessage = '❌ Veuillez remplir tous les champs.';
    }
  }
  
}
