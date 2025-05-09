import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
@Component({
  selector: 'app-nav-back',
  standalone: true,
  imports: [RouterOutlet,
    RouterLink,
    RouterLinkActive],
  templateUrl: './nav-back.component.html',
  styleUrls: ['./nav-back.component.css']
})
export class NavBackComponent {
  constructor(private router: Router) {}
  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']); // Redirect to '/offres' after logout
  }

}
