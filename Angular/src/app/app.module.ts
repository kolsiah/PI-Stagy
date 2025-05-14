import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AsideComponent } from './components/Back/aside/aside.component';
import { FooterBackComponent } from './components/Back/footer-back/footer-back.component';
import { HomeBackComponent } from './components/Back/home-back/home-back.component';
import { NavBackComponent } from './components/Back/nav-back/nav-back.component';
import { SettingsComponent } from './components/Back/settings/settings.component';
import { HttpClientModule } from '@angular/common/http';
import { InternshipsBackComponent } from './components/Back/internships-back/internships-back.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HomeFrontComponent } from './components/Front/home-front/home-front.component';
import { FooterComponent } from './components/Front/footer/footer.component';
import { InternshipsComponent } from './components/Front/internships/internships.component';
import { NavbarComponent } from './components/Front/navbar/navbar.component';
import { StartProcessComponent } from './components/Front/start-process/start-process.component';
import { SuggestionsHComponent } from './components/Front/suggestions-h/suggestions-h.component';
import { TasksComponent } from './components/Front/tasks/tasks.component';
import { WelcomeViewComponent } from './components/Front/welcome-view/welcome-view.component';
import { ContactComponent } from './components/Front/contact/contact.component';
import { AboutComponent } from './components/Front/about/about.component';
import { NavbarStudentComponent } from './components/Front/navbar-student/navbar-student.component';
import { NavbarHrComponent } from './components/Front/navbar-hr/navbar-hr.component';
import { HomeHrComponent } from './components/Front/hrDashboard/home-hr/home-hr.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { OffreButtonsComponent } from './components/Front/OffreStage/offre-buttons/offre-buttons.component';
import { OffreFormComponent } from './components/Front/OffreStage/offre-form/offre-form.component';
import { OffreDetailsComponent } from './components/Front/OffreStage/offre-details/offre-details.component';
import { OffreCardComponent } from './components/Front/OffreStage/offre-card/offre-card.component';
import { OffreListComponent } from './components/Front/OffreStage/offre-list/offre-list.component';
import { ListOffreStudentComponent } from './components/Front/OffreStage/list-offre-student/list-offre-student.component';
import { CondidatureComponent } from './components/Front/condidature/condidature.component';
import { SignInComponent } from './components/Front/sign-in/sign-in.component';
import { CreateUserComponent } from './components/Front/create-user/create-user.component';
import { CreateUserDialogComponent } from './components/Front/create-user-dialog/create-user-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { InetrviewListComponent } from './components/Front/condidature/inetrview-list/inetrview-list.component';
import { FullCalendarModule } from '@fullcalendar/angular';
import { InterviewCalendarComponent } from './components/Front/condidature/interview-calendar/interview-calendar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AddDocumentFComponent } from './components/Front/Document/add-document-f/add-document-f.component';
import { ListDocFComponent } from './components/Front/Document/list-doc-f/list-doc-f.component';
import { ListValFComponent } from './components/Front/Document/list-val-f/list-val-f.component';
import { ListDocEnsComponent } from './components/Front/Document/list-doc-ens/list-doc-ens.component';
import { AddValidationProfComponent } from './components/Front/Document/add-validation-prof/add-validation-prof.component';
import { ListValEtudComponent } from './components/Front/Document/list-val-etud/list-val-etud.component';
import { NgChartsModule } from 'ng2-charts';

@NgModule({
  declarations: [
    AppComponent,
    HomeFrontComponent,
    HomeFrontComponent,
    FooterComponent,
    InternshipsComponent,
    NavbarComponent,
    StartProcessComponent,
    SuggestionsHComponent,
    TasksComponent,
    WelcomeViewComponent,
    TasksComponent,
    ContactComponent,
    AboutComponent,
    NavbarStudentComponent,
    NavbarHrComponent,
    HomeHrComponent,
    OffreListComponent,
    OffreCardComponent,
    OffreDetailsComponent,
    OffreFormComponent,
    OffreButtonsComponent,
    ListOffreStudentComponent,
    CondidatureComponent,
    SignInComponent,
    CreateUserComponent,
    CreateUserDialogComponent,
    InetrviewListComponent,
    InterviewCalendarComponent,
    AddDocumentFComponent,
    ListDocFComponent,
    ListValFComponent,
    ListDocEnsComponent,
    AddValidationProfComponent,
    ListValEtudComponent
  ],
  imports: [
    NgxPaginationModule,
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    AsideComponent,
    InternshipsBackComponent,
    FooterBackComponent,
    HomeBackComponent,
    NavBackComponent,
    SettingsComponent,
    NgbModule,
    MatDialogModule,
    FullCalendarModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgChartsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
