import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeFrontComponent } from './components/Front/home-front/home-front.component';
import { WelcomeViewComponent } from './components/Front/welcome-view/welcome-view.component';
import { StartProcessComponent } from './components/Front/start-process/start-process.component';
import { HomeHrComponent } from './components/Front/hrDashboard/home-hr/home-hr.component';
import { OffreListComponent } from './components/Front/OffreStage/offre-list/offre-list.component';
import { OffreDetailsComponent } from './components/Front/OffreStage/offre-details/offre-details.component';
import { OffreFormComponent } from './components/Front/OffreStage/offre-form/offre-form.component';
import { ListOffreStudentComponent } from './components/Front/OffreStage/list-offre-student/list-offre-student.component';
import { CondidatureComponent } from './components/Front/condidature/condidature.component';
import { SignInComponent } from './components/Front/sign-in/sign-in.component';
import { CreateUserComponent } from './components/Front/create-user/create-user.component';
import { InetrviewListComponent } from './components/Front/condidature/inetrview-list/inetrview-list.component';
import { InterviewCalendarComponent } from './components/Front/condidature/interview-calendar/interview-calendar.component';
import { HomeBackComponent } from './components/Back/home-back/home-back.component';
import { InternshipsBackComponent } from './components/Back/internships-back/internships-back.component';
import { AddDocumentFComponent } from './components/Front/Document/add-document-f/add-document-f.component';
import { ListDocFComponent } from './components/Front/Document/list-doc-f/list-doc-f.component';
import { ListValFComponent } from './components/Front/Document/list-val-f/list-val-f.component';
import { ListDocEnsComponent } from './components/Front/Document/list-doc-ens/list-doc-ens.component';
import { AddValidationProfComponent } from './components/Front/Document/add-validation-prof/add-validation-prof.component';
import { ListValEtudComponent } from './components/Front/Document/list-val-etud/list-val-etud.component';

const routes: Routes = [
  { path: '', redirectTo: '/sign-in', pathMatch: 'full' },
  { path: 'homeFront', component: HomeFrontComponent },
  { path: 'welcomeV', component: WelcomeViewComponent },
  { path: 'startProcess', component: StartProcessComponent },
  { path: 'HrDashboard', component: HomeHrComponent },
  { path: 'sign-in', component: SignInComponent },
  { path: 'create-user', component: CreateUserComponent },

  { path: 'offres', component: OffreListComponent }, 
  { path: 'offre/:id', component: OffreDetailsComponent },
  { path: 'add-offre', component: OffreFormComponent },
  { path: 'edit-offre/:id', component: OffreFormComponent }, 
  { path: 'offreStudent', component: ListOffreStudentComponent }, 
  { path: 'candidatures', component: CondidatureComponent },
  { path: 'interview', component: InetrviewListComponent },
  { path: 'calendar', component: InterviewCalendarComponent },
  {path :'Back',component:HomeBackComponent},
  {path : 'internshipsBack',component:InternshipsBackComponent},
  {path : 'document/add',component:AddDocumentFComponent},
  {path:'document/list',component:ListDocFComponent},
  {
    path: 'back/documents',
    loadChildren: () => import('./features/document/document.module').then(m => m.DocumentModule)
  },
  {path:'validation/list',component:ListValFComponent},
  {path:'document/enseignant', component:ListDocEnsComponent},
  {path:'validation/enseignant', component:AddValidationProfComponent},
  {path:'validation/etudiant', component:ListValEtudComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
