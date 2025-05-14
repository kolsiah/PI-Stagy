import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-create-user-dialog',
  templateUrl: './create-user-dialog.component.html',
})
export class CreateUserDialogComponent {
  createUserForm: FormGroup;
  errorMessage: string = '';

  constructor(
    @Inject(MatDialogRef) public dialogRef: MatDialogRef<CreateUserDialogComponent>,
    private fb: FormBuilder
  ) {
    this.createUserForm = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.createUserForm.valid) {
      console.log('Form Data:', this.createUserForm.value);
      this.dialogRef.close();
    } else {
      this.errorMessage = 'Please fill out all fields correctly.';
    }
  }
}