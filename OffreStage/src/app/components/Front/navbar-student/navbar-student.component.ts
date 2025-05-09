import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar-student',
  templateUrl: './navbar-student.component.html',
  styleUrls: ['./navbar-student.component.css']
})
export class NavbarStudentComponent implements OnInit{
  userRole: string | null = null;
  userId : number | null = null;
  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.userRole = this.authService.getUserRoleFromToken();
    this.userId = this.authService.getUserIdFromToken();
    console.log('✅ Rôle récupéré depuis le token :', this.userRole);
    console.log('✅ ID utilisateur récupéré depuis le token :', this.userId);
  }
}
