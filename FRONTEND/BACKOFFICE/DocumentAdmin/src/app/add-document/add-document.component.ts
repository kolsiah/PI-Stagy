import { Component } from '@angular/core';
import { FormGroup,FormBuilder,Validators } from '@angular/forms';
import { DocumentService } from '../Services/document.service';

@Component({
  selector: 'app-add-document',
  templateUrl: './add-document.component.html',
  styleUrls: ['./add-document.component.scss']
})
export class AddDocumentComponent {
documentForm: FormGroup;
file: File | null = null;
uploadMessage: string = '';
constructor(private fb: FormBuilder, private documentService: DocumentService) {
  this.documentForm = this.fb.group({
    titreDocument: ['', [Validators.required, Validators.minLength(10)]],
    typeDocument: ['', Validators.required],
    statutDocument: ['', Validators.required],
    etudiantId: ['', [Validators.required, Validators.pattern("^[0-9]+$")]],
    stageId: ['', [Validators.required, Validators.pattern("^[0-9]+$")]],
    file: [null, Validators.required]
  });
}
onFileChange(event: any) {
  const selectedFile = event.target.files[0];

  if (selectedFile) {
    this.file = selectedFile;
  }
}
uploadDocument() {
  if (!this.file) {
    this.uploadMessage = 'Please select a file!';
    return;
  }

  const formData = new FormData();
  formData.append('file', this.file);
  formData.append('titreDocument', this.documentForm.get('titreDocument')?.value);
  formData.append('typeDocument', this.documentForm.get('typeDocument')?.value);
  formData.append('etudiantId', String(this.documentForm.get('etudiantId')?.value || ''));
  formData.append('stageId', String(this.documentForm.get('stageId')?.value || ''));
  formData.append('statutDocument', this.documentForm.get('statutDocument')?.value);
  this.documentService.uploadDocument(formData).subscribe({
    next: (response) => {
      console.log("File uploaded successfully:", response);
      this.uploadMessage = response;
    },
    error: (error) => {
      console.error("Error uploading file:", error);

      let errorMessage = "Error uploading file!";
      if (error.error && error.error.message) {
        errorMessage += " " + error.error.message;
      } else if (error.message) {
        errorMessage += " " + error.message;
      }

      this.uploadMessage = errorMessage;
    }
  });
}
}
