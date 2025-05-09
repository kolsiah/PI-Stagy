import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
})
export class CreateUserComponent {
  createUserForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.createUserForm = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['', Validators.required],
      notRobot: [false, Validators.requiredTrue] // ✅ Checkbox must be checked
    });
  }

  onSubmit() {
    if (this.createUserForm.invalid) {
      this.errorMessage = 'Please fill in all fields correctly and confirm you are not a robot.';
      return;
    }

    const userData = this.createUserForm.value;

    this.authService.signUp(userData).subscribe({
      next: () => {
        alert('User registered successfully!');
        this.router.navigate(['/offres']); // Redirect to '/offres' after successful user creation
      },
      error: (error: any) => {
        console.error('Registration failed:', error);
        this.errorMessage = 'Registration failed. Please try again.';
      }
    });
  }
}
