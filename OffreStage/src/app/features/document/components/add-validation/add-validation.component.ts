import { Component } from '@angular/core';
import { FormGroup,Validators,FormBuilder } from '@angular/forms';
import { ValdationService } from '../../services/Validation/valdation.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-validation',
  templateUrl: './add-validation.component.html',
  styleUrls: ['./add-validation.component.scss']
})
export class AddValidationComponent {
  validationForm!: FormGroup;
  idDocument: number =0; // To store idDocument from the route
  responseMessage: string="";

  // Statut options for dropdown
  statutOptions = ['validé', 'rejeté', 'en_attente'];

  constructor(
    private formBuilder: FormBuilder,
    private validationService: ValdationService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Fetching idDocument from the route parameter
    this.route.params.subscribe(params => {
      this.idDocument = +params['idDocument']; // Convert to number
    });

    // Initializing the form with validation
    this.validationForm = this.formBuilder.group({
      idEncadrant: [null, Validators.required],
      statut: ['validé', Validators.required],
      commentaire: ['', Validators.required],
      etudiantId: [null, Validators.required],
      offreId: [null, Validators.required]
    });
    
  }

  onValidateDocument(): void {
    if (this.validationForm.valid) {
      const {
        idEncadrant,
        statut,
        commentaire,
        etudiantId,
        offreId
      } = this.validationForm.value;
  
      this.validationService.validateDocument(
        this.idDocument,
        idEncadrant,
        statut,
        commentaire,
        etudiantId,
        offreId
      ).subscribe(
        (response) => {
          this.responseMessage = response;
        },
        (error) => {
          this.responseMessage = `Error: ${error.message}`;
        }
      );
    } else {
      this.responseMessage = 'Please fill in all required fields.';
    }
  }
  
}