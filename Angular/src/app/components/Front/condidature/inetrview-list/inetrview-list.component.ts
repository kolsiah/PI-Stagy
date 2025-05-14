import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { InterviewService } from 'src/app/services/interview.service';

@Component({
  selector: 'app-inetrview-list',
  templateUrl: './inetrview-list.component.html',
  styleUrls: ['./inetrview-list.component.css']
})
export class InetrviewListComponent {
  interviews: any[] = [];

  constructor(private interviewService: InterviewService,private router: Router) {}

  ngOnInit(): void {
    this.loadCandidatures();
  }

  loadCandidatures(): void {
      const userId = 1; // Replace with the actual student ID
      this.interviewService.afficherInterview().subscribe(
        (data: any[]) => {
          this.interviews = data;
        }
      );
    }

    goToCalendar() {
      this.router.navigate(['/calendar']);
    }
}
