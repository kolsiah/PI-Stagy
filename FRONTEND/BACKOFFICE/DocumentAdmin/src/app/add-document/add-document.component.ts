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
      file: [null]
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
        this.documentForm.reset();
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
/*export class AddDocumentComponent {
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
      file: [null]
    });
  }
  onFileSelected(event: any) {
    if (event.target.files.length > 0) {
      this.file = event.target.files[0];
      this.documentForm.patchValue({
        file: this.file
      });
    } else {
      this.file = null;
      this.documentForm.patchValue({
        file: null
      });
    }
    console.log("File Selected:", this.file);
  }
  
  
  
  // Submit form and upload document
  onSubmit() {
    if (this.documentForm.valid && this.file) {
      const formData = new FormData();
      formData.append('file', this.file);
      formData.append('titreDocument', this.documentForm.value.titreDocument);
      formData.append('typeDocument', this.documentForm.value.typeDocument);
      formData.append('statutDocument', this.documentForm.value.statutDocument);
      formData.append('etudiantId', this.documentForm.value.etudiantId);
      formData.append('stageId', this.documentForm.value.stageId);
  
      this.documentService.uploadDocument(formData).subscribe({
        next: (response) => {
          this.uploadMessage = response;
          console.log("File uploaded:", response);
          this.documentForm.reset();  // ✅ Reset form fields
          this.file = null;  // ✅ Reset file variable
        },
        error: (error) => {
          this.uploadMessage = "Error uploading file!";
          console.error("Upload error:", error);
        }
      });
    } else {
      this.uploadMessage = "Please fill out all fields correctly.";
      console.log("Form Invalid:", this.documentForm.invalid);
      console.log("Invalid Controls:", this.documentForm.controls);
      console.log("file Errors:", this.documentForm.get('file')?.errors);
    }
  }
  
  
  }*/