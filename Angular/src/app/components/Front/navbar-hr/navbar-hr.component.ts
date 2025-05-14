import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-navbar-hr',
  templateUrl: './navbar-hr.component.html',
  styleUrls: ['./navbar-hr.component.css']
})
export class NavbarHrComponent implements OnInit {
  userRole: string | null = null;
  userId : number | null = null;
  
  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    const role = this.authService.getUserRoleFromToken();
    this.userRole = role ? role : 'Guest'; // Default to 'Guest' if undefined
    this.userRole = this.authService.getUserRoleFromToken();
    this.userId = this.authService.getUserIdFromToken();
    console.log('✅ Rôle récupéré depuis le token :', this.userRole);
    console.log('✅ ID utilisateur récupéré depuis le token :', this.userId);
  
  }
}