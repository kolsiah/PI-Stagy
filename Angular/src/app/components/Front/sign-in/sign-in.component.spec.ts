import { Component, OnDestroy } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CreateUserDialogComponent } from '../create-user-dialog/create-user-dialog.component';
import { Subscription, timer, interval } from 'rxjs';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
})
export class SignInComponent implements OnDestroy {
  email: string = '';
  password: string = '';
  isAccountLocked: boolean = false;
  errorMessage: string = '';
  failedAttempts: number = 0;
  attemptsLeft: number = 3;
  remainingTime: number = 180; // 3 minutes in seconds
  private unlockSubscription: Subscription | null = null;
  private countdownSubscription: Subscription | null = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    public dialog: MatDialog
  ) {}

  onSubmit() {
    if (this.isAccountLocked) {
      return;
    }

    this.authService.signIn(this.email, this.password).subscribe({
      next: (response) => {
        console.log('Sign-in successful', response);
        // Reset failed attempts on successful login
        this.failedAttempts = 0;
        this.attemptsLeft = 3;
        localStorage.setItem('token', response.token);
        this.router.navigate(['/homeFront']); // Redirect to '/offres' after successful sign-in
      },
      error: (error) => {
        console.error('Sign-in failed', error);
        
        // Increment failed attempts
        this.failedAttempts++;
        this.attemptsLeft = 3 - this.failedAttempts;
        
        // Check if account should be locked
        if (this.failedAttempts >= 3) {
          this.lockAccount();
          this.errorMessage = 'Too many failed attempts. Account locked for 3 minutes.';
        } else {
          // Show regular error message
          this.errorMessage = error.message || 'Invalid email or password';
        }
      },
    });
  }

  private lockAccount() {
    this.isAccountLocked = true;
    this.remainingTime = 180; // Reset to 3 minutes
    
    // Start countdown timer
    this.countdownSubscription = interval(1000).subscribe(() => {
      this.remainingTime--;
      if (this.remainingTime <= 0) {
        this.unlockAccount();
      }
    });
    
    // Set timer to automatically unlock after 3 minutes
    this.unlockSubscription = timer(180000).subscribe(() => {
      this.unlockAccount();
    });
  }

  private unlockAccount() {
    this.isAccountLocked = false;
    this.failedAttempts = 0;
    this.attemptsLeft = 3;
    this.errorMessage = '';
    
    // Clean up subscriptions
    if (this.unlockSubscription) {
      this.unlockSubscription.unsubscribe();
      this.unlockSubscription = null;
    }
    
    if (this.countdownSubscription) {
      this.countdownSubscription.unsubscribe();
      this.countdownSubscription = null;
    }
  }

  openCreateUserDialog(): void {
    this.dialog.open(CreateUserDialogComponent, {
      width: '700px',
    });
  }

  ngOnDestroy() {
    // Clean up subscriptions when component is destroyed
    if (this.unlockSubscription) {
      this.unlockSubscription.unsubscribe();
    }
    if (this.countdownSubscription) {
      this.countdownSubscription.unsubscribe();
    }
  }
}