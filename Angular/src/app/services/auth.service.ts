import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode'; // Updated import to use named import

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8081/user/auth';

  constructor(private http: HttpClient) {}

  // Sign-In Method
  signIn(email: string, password: string): Observable<any> {
    const signInRequest = { email, password };
    return this.http.post(`${this.baseUrl}/signin`, signInRequest).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'Your account is locked. Please check your email to unlock it.';
        if (error.error && error.error.message) {
          errorMessage = error.error.message;
        }
        return throwError(() => new Error(errorMessage));
      }),
      map((response: any) => {
        // ✅ Sauvegarder le token dans le localStorage
        if (response && response.token) {
          localStorage.setItem('token', response.token);
        }
  
        // 🔄 Optionnel : tu peux encore garder les autres infos si nécessaires
        if (response && response.role) {
          localStorage.setItem('userRole', response.role);
          localStorage.setItem('user', JSON.stringify(response.user));
        }
  
        return response;
      })
    );
  }
  
  

  // Sign-Up Method
  signUp(userData: { nom: string; prenom: string; email: string; password: string; role: string }): Observable<any> {
    if (!userData.role) {
      return throwError(() => new Error('Role is required.'));
    }

    const signUpRequest = {
      nom: userData.nom,
      prenom: userData.prenom,
      email: userData.email,
      password: userData.password,
      roles: [userData.role], // Send roles as an array
    };
    return this.http.post(`${this.baseUrl}/signup`, signUpRequest).pipe(
      catchError(this.handleError) // Handle errors
    );
  }

  // Unlock Account Method
  unlockAccount(email: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/unlock?email=${email}`).pipe(
      catchError(this.handleError) // Handle errors
    );
  }

  // Get User Role from Token
  getUserRoleFromToken(): string | null {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const decodedToken: any = jwtDecode(token); // Updated usage to match named import
        return decodedToken.role || null;
      } catch (error) {
        console.error('Error decoding token:', error);
        return null;
      }
    }
    return null;
  }
  getUserIdFromToken(): number | null {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const decodedToken: any = jwtDecode(token); // Updated usage to match named import
        return decodedToken.userId || null;
      } catch (error) {
        console.error('Error decoding token:', error);
        return null;
      }
    }
    return null;
  }

  // Handle HTTP errors
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = error.error?.message || error.message;
    }
    return throwError(() => new Error(errorMessage));
  }
  getUserNameById(userId: number): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/userNom/${userId}`);
  }
}