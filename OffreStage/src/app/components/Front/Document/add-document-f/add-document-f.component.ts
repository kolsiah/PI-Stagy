import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DocumentService } from 'src/app/features/document/services/document.service';
import { AuthService } from 'src/app/services/auth.service';
import { ActivatedRoute } from '@angular/router'; // ✅ pour lire les queryParams
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-document-f',
  templateUrl: './add-document-f.component.html',
  styleUrls: ['./add-document-f.component.css']
})
export class AddDocumentFComponent implements OnInit {
  documentForm!: FormGroup;
  file: File | null = null;
  uploadMessage: string = '';
  userRole: string | null = null;
  userId: number | null = null;
  stageId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private documentService: DocumentService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private location: Location

  ) {}

  ngOnInit(): void {
    this.userRole = this.authService.getUserRoleFromToken();
      // 🔓 lire les paramètres de l’URL
      this.route.queryParams.subscribe(params => {
        this.userId = +params['userId'] || null;
        this.stageId = +params['stageId'] || null;
      });
  
      this.userRole = this.authService.getUserRoleFromToken();
    this.documentForm = this.fb.group({
      typeDocument: ['', Validators.required],
      titreDocument: ['', [Validators.required, Validators.minLength(10)]],
      statutDocument: ['en_attente'],
      etudiantId: [this.userId, [Validators.required, Validators.pattern("^[0-9]+$")]],
      stageId: [this.stageId, [Validators.required, Validators.pattern("^[0-9]+$")]],
      file: [null]
    });
  
    // 🟡 Réagir au changement de type
    this.documentForm.get('typeDocument')?.valueChanges.subscribe(type => {
      if (type === 'cv') {
        this.documentForm.get('titreDocument')?.clearValidators();
        this.documentForm.get('statutDocument')?.clearValidators();
      } else {
        this.documentForm.get('titreDocument')?.setValidators([Validators.required, Validators.minLength(10)]);
        this.documentForm.get('statutDocument')?.setValidators([Validators.required]);
      }
  
      this.documentForm.get('titreDocument')?.updateValueAndValidity();
      this.documentForm.get('statutDocument')?.updateValueAndValidity();
    });
  }
  goBack(): void {
    this.location.back();
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
    formData.append('etudiantId', String(this.userId)); // ✅ depuis URL
    formData.append('stageId', String(this.stageId));   // ✅ depuis URL
    formData.append('statutDocument', this.documentForm.get('statutDocument')?.value);

    this.documentService.uploadDocument(formData).subscribe({
      next: (response) => {
        console.log("✅ File uploaded successfully:", response);
        this.uploadMessage = response;
        this.documentForm.reset();
      },
      error: (error) => {
        console.error("❌ Error uploading file:", error);
        this.uploadMessage = "Error uploading file: " + (error.error?.message || error.message);
      }
    });
  }
}
