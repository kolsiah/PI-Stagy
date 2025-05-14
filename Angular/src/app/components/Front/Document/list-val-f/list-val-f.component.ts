import { Component,OnInit } from '@angular/core';
import { Validationnn } from 'src/app/features/document/Model/ValidationMod';
import { ValdationService } from 'src/app/features/document/services/Validation/valdation.service';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { ChartConfiguration, ChartOptions, ChartType} from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import { Chart } from 'chart.js';
import { DocumentService } from 'src/app/features/document/services/document.service';
import { OffreStageService } from 'src/app/services/offre-stage.service';
Chart.register(ChartDataLabels);

@Component({
  selector: 'app-list-val-f',
  templateUrl: './list-val-f.component.html',
  styleUrls: ['./list-val-f.component.css']
})
export class ListValFComponent implements OnInit {
  ListVal: Validationnn[] = [];
  selectedValidation: Validationnn = new Validationnn();
  modal: any;
  userRole: string | null = null;
  userId : number | null = null;
  userNames: { [id: number]: string } = {};
  selectedComment: string = '';
  searchValue: string = '';
  selectedCriteria: string = 'etudiantId';
  sortAsc: boolean = true;
  resultatAnalyse: string = '';
  commentaireAnalyseId: number | null = null;
  documentTitles: { [id: number]: string } = {};
  offreTitles: { [id: number]: string } = {};

   // ✅ Données du graphique
   chartLabels: string[] = ['Positifs', 'Négatifs'];
   chartData = {
     labels: this.chartLabels,
     datasets: [
       {
         data: [0, 0],
         backgroundColor: ['#4CAF50', '#F44336'],
       },
     ],
   };
 
   chartOptions: ChartOptions<'pie'> = {
     responsive: true,
     plugins: {
       legend: {
         position: 'bottom',
         labels: {
           color: '#000'
         }
       },
       datalabels: {
         formatter: (value: number, context) => {
           const total = context.chart.data.datasets[0].data.reduce((a: any, b: any) => a + b, 0);
           const percentage = total ? (value / total * 100).toFixed(1) + '%' : '0%';
           return percentage;
         },
         color: '#fff',
         font: {
           weight: 'bold' as const,
           size: 14
         }
       }
     }
   };
 

  constructor(private LV: ValdationService, private toastr: ToastrService,private authService: AuthService,  private docService: DocumentService, private offreService: OffreStageService) {}

  ngOnInit(): void {
    this.userRole = this.authService.getUserRoleFromToken();
    this.userId = this.authService.getUserIdFromToken();
    console.log('✅ Rôle récupéré depuis le token :', this.userRole);
    console.log('✅ ID utilisateur récupéré depuis le token :', this.userId);
      this.loadValidations();
  }
  loadValidations() {
    this.LV.getValidationByENCId(this.userId!).subscribe({
      next: (data) => {
        this.ListVal = data;
  
        data.forEach(val => {
          this.getUserName(val.etudiantId);
          this.getUserName(val.idEncadrant);
        });
  
        this.loadDocumentTitles();  
        this.loadOffreTitles();
      },
      error: (err) => {
        console.error("Erreur lors du chargement des validations", err);
      }
    });
  }

  loadOffreTitles(): void {
    this.ListVal.forEach(val => {
      if (!this.offreTitles[val.offreId]) {
        this.offreService.getOffreById(val.offreId).subscribe(offre => {
          this.offreTitles[val.offreId] = offre.titre || 'Offre';
        });
      }
    });
  }  
  
  public ChartDataLabels = ChartDataLabels;
  loadDocumentTitles(): void {
    this.ListVal.forEach(val => {
      if (val.idDocument != null) {
        this.docService.getDocumentById(val.idDocument).subscribe(doc => {
          this.documentTitles[val.idDocument] = doc.titreDocument || 'Sans titre';
        });
      }
    });
  }
    
  analyserTousCommentaires(): void {
    const commentaires = this.ListVal.map(val => val.commentaire).filter(c => !!c);
    this.LV.analyserCommentaires(commentaires).subscribe({
      next: (res) => {
        this.chartData = {
          labels: ['Positifs', 'Négatifs'],
          datasets: [
            {
              data: [res.positif, res.negatif],
              backgroundColor: ['#4CAF50', '#F44336'],
            },
          ],
        };

        const modalElement = document.getElementById('analyseModal');
        if (modalElement) {
          const modal = new (window as any).bootstrap.Modal(modalElement);
          modal.show();
        } else {
          console.error("❌ Modal 'analyseModal' introuvable dans le DOM.");
        }
      },
      error: () => {
        alert("Erreur lors de l'analyse des commentaires");
      }
    });
  }

  
  
  analyserCommentaire(id: number): void {
    this.LV.analyseCommentaire(id).subscribe({
      next: (res) => {
        this.commentaireAnalyseId = id;
        this.resultatAnalyse = res;
      },
      error: (err) => {
        alert("Erreur lors de l’analyse.");
        console.error(err);
      }
    });
  }
  getFilteredValidations() {
    return this.ListVal
      .filter(val => {
        const value = this.searchValue.toLowerCase();
        switch (this.selectedCriteria) {
          case 'etudiantId':
            return val.etudiantId?.toString().includes(value);
          case 'offreId':
            return val.offreId?.toString().includes(value);
          case 'idDocument':
            return val.idDocument?.toString().includes(value);
          case 'statut':
            return val.statut?.toLowerCase().includes(value);
          case 'offreTitre':
              const titre = this.offreTitles[val.offreId]?.toLowerCase() || '';
              return titre.includes(value);  
          default:
            return true;
        }
      });
  }
  
  // ↕️ Tri par date
  sortValidations(): void {
    this.sortAsc = !this.sortAsc;
    this.ListVal.sort((a, b) => {
      const dateA = new Date(a.dateValidation).getTime();
      const dateB = new Date(b.dateValidation).getTime();
      return this.sortAsc ? dateA - dateB : dateB - dateA;
    });
  }
  openCommentModal(comment: string): void {
    this.selectedComment = comment;
    const modalEl = document.getElementById('commentModal');
    if (modalEl) {
      const bootstrapModal = new window.bootstrap.Modal(modalEl);
      bootstrapModal.show();
    }
  }

  getUserName(userId: number): void {
    if (!this.userNames[userId]) {
      this.authService.getUserNameById(userId).subscribe({
        next: (name) => {
          this.userNames[userId] = name;
        },
        error: () => {
          this.userNames[userId] = 'Utilisateur inconnu';
        }
      });
    }
  }
      
  updateValidation() {
    this.LV.updateValidation(this.selectedValidation.idValidation, this.selectedValidation).subscribe({
      next: (res) => {
  
        // 🔥 Correction ici : Blur actif avant de fermer
        if (document.activeElement instanceof HTMLElement) {
          document.activeElement.blur();
        }
  
        this.modal.hide();
        this.loadValidations();
  
        setTimeout(() => document.body.focus(), 100); // Focus neutre propre
        this.toastr.success('Validation modifiée avec succès ✅', 'Succès');
      },
      error: (err) => {
        console.error("❌ Erreur modification :", err);
        this.toastr.error('Erreur lors de la mise à jour ❌', 'Erreur');
      }
    });
  }
  
  
  // 🗑️ Fonction pour supprimer une validation
  deleteValidation(idValidation: number) {
    if (confirm("⚠️ Êtes-vous sûr de vouloir supprimer cette validation ?")) {
      this.LV.deleteValidation(idValidation).subscribe({
        next: (response) => {
          console.log("✅ Validation supprimée :", response);
          this.loadValidations(); // 🔄 Recharger la liste après suppression
        },
        error: (error) => {
          console.error("❌ Erreur lors de la suppression :", error);
          alert("Erreur lors de la suppression !");
        }
      });
    }
  }
}

