import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentService } from 'src/app/features/document/services/document.service';
import { Document } from 'src/app/features/document/Model/DocumentMod';
import { FormGroup,FormBuilder,Validators } from '@angular/forms';
@Component({
  selector: 'app-update-document',
  templateUrl: './update-document.component.html',
  styleUrls: ['./update-document.component.scss']
})
export class UpdateDocumentComponent implements OnInit{
documentId : number=0;
documentForm !: FormGroup;
file: File | null = null;
uploadMessage:String = '';
constructor(
  private LL : DocumentService,
  private RL : ActivatedRoute,
  private route : Router,
  private fb : FormBuilder
  ){}
ngOnInit(): void {
  const id = this.RL.snapshot.paramMap.get('idDocument');
  if (id) {
    this.documentId = +id;
    this.loadDocument();
  } else {
    console.error('No document ID found in route params');
  }

  this.documentForm = this.fb.group({
    titreDocument: ['', [Validators.required, Validators.minLength(10)]],
    typeDocument: ['', Validators.required],
    statutDocument: ['', Validators.required],
    etudiantId: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]],
    stageId: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]],
    urlDocument: ['']
  });}
  loadDocument(): void {
    this.LL.getDocumentById(this.documentId).subscribe({
      next: (document: Document) => {
        this.documentForm.patchValue({
          titreDocument: document.titreDocument,
          typeDocument: document.typeDocument,
          statutDocument: document.statutDocument,
          etudiantId: document.etudiantId,
          stageId: document.stageId,
          urlDocument: document.urlDocument
        });
      },
      error : (error) => {
        console.error('Error loading document', error);
      }
    });
  }
  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.file = file;
    }
  }

  updateDocument(): void {
    if (this.documentForm.invalid) {
      return;
    }
  
    const updatedDocument: Document = {
      ...this.documentForm.value,
      idDocument: this.documentId, // Include the document ID in the object
      file: this.file // You can keep this if you plan to handle the file upload
    };
  
    // Pass the updated document to the service method
    this.LL.updateDocument(updatedDocument).subscribe({
      next: (response) => {
        console.log('Document updated successfully', response);
        this.route.navigate(['/list']);
      },
      error: (error) => {
        console.error('Error updating document', error);
      },
      complete: () => {
        console.log('Update document operation complete');
      }
    });
  }
}

