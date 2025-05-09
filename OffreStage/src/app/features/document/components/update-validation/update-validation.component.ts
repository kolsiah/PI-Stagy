import { Component,OnInit } from '@angular/core';
import { ValdationService } from 'src/app/features/document/services/Validation/valdation.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Validationnn } from 'src/app/features/document/Model/ValidationMod';

@Component({
  selector: 'app-update-validation',
  templateUrl: './update-validation.component.html',
  styleUrls: ['./update-validation.component.scss']
})
export class UpdateValidationComponent implements OnInit {

  validation: Validationnn = new Validationnn();
  idValidation!: number;

  constructor(private validationService: ValdationService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    this.idValidation = this.route.snapshot.params['idValidation'];

    this.validationService.getValidations().subscribe(data => {
      const found = data.find(v => v.idValidation === this.idValidation);
      if (found) {
        this.validation = found;
      } else {
        alert("Validation not found!");
      }
    });
  }

  onSubmit() {
    this.validationService.updateValidation(this.idValidation, this.validation).subscribe({
      next: (response) => {
        console.log("✅ Validation updated", response);
        this.router.navigate(['/list-validation']); // Après modification, retourne à la liste
      },
      error: (err) => {
        console.error("❌ Error updating validation", err);
        alert("Update failed!");
      }
    });
  }
}