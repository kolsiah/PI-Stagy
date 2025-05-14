import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ValdationService } from '../../services/Validation/valdation.service';
import { Validationnn } from '../../Model/ValidationMod';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
declare var window: any;

@Component({
  selector: 'app-list-validation',
  templateUrl: './list-validation.component.html',
  styleUrls: ['./list-validation.component.scss']
})
export class ListValidationComponent implements OnInit {

  ListVal: Validationnn[] = [];
  selectedValidation: Validationnn = new Validationnn(); // Pour stocker la validation sélectionnée
  modal: any;
  userNames: { [id: string]: string } = {}; // ✅ Utilisation explicite de string comme clé

  constructor(
    private LV: ValdationService,
    private toastr: ToastrService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadValidations(); // ⬅️ Recharger la liste
    this.modal = new window.bootstrap.Modal(
      document.getElementById('editValidationModal')
    );
  }

  getUserName(userId: number): void {
    const key = String(userId); // ✅ Convertir l’ID en string

    if (!this.userNames[key]) {
      this.authService.getUserNameById(userId).subscribe({
        next: (name) => {
          this.userNames[key] = name;
          this.cdr.detectChanges(); // Forcer le rafraîchissement de l'affichage
          console.log('Nom récupéré:', key, '→', name);
        },
        error: () => {
          this.userNames[key] = 'Utilisateur inconnu';
          this.cdr.detectChanges();
          console.log('Erreur récupération nom pour ID', key);
        }
      });
    }
  }

  loadValidations(): void {
    this.LV.getValidations().subscribe({
      next: (data) => {
        this.ListVal = data;
        data.forEach(val => {
          this.getUserName(val.etudiantId);
          this.getUserName(val.idEncadrant);
        });
      },
      error: (err) => {
        console.error("Erreur lors du chargement des validations", err);
      }
    });
  }

  openEditModal(validation: Validationnn) {
    this.selectedValidation = { ...validation };
    this.modal.show();
  }

  updateValidation() {
    this.LV.updateValidation(this.selectedValidation.idValidation, this.selectedValidation).subscribe({
      next: () => {
        if (document.activeElement instanceof HTMLElement) {
          document.activeElement.blur();
        }

        this.modal.hide();
        this.loadValidations();

        setTimeout(() => document.body.focus(), 100);
        this.toastr.success('Validation modifiée avec succès ✅', 'Succès');
      },
      error: (err) => {
        console.error("❌ Erreur modification :", err);
        this.toastr.error('Erreur lors de la mise à jour ❌', 'Erreur');
      }
    });
  }

  deleteValidation(idValidation: number) {
    if (confirm("⚠️ Êtes-vous sûr de vouloir supprimer cette validation ?")) {
      this.LV.deleteValidation(idValidation).subscribe({
        next: () => {
          console.log("✅ Validation supprimée");
          this.loadValidations();
        },
        error: (error) => {
          console.error("❌ Erreur lors de la suppression :", error);
          alert("Erreur lors de la suppression !");
        }
      });
    }
  }
}
