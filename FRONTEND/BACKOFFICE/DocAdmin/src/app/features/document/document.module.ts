import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DocumentRoutingModule } from './document-routing.module';
import { AddDocumentComponent } from './components/add-document/add-document.component';
import { ListDocumentComponent } from './components/list-document/list-document.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from "../../shared/shared.module";
import { UpdateDocumentComponent } from './components/update-document/update-document.component';


@NgModule({
  declarations: [
    AddDocumentComponent,
    ListDocumentComponent,
    UpdateDocumentComponent
  ],
  imports: [
    CommonModule,
    DocumentRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    SharedModule,
    FormsModule
],
  exports: [
    AddDocumentComponent,
    ListDocumentComponent
  ]
})
export class DocumentModule { }
