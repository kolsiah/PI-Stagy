import { Component, OnInit } from '@angular/core';
import { CalendarOptions } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { InterviewService } from 'src/app/services/interview.service';

@Component({
  selector: 'app-interview-calendar',
  templateUrl: './interview-calendar.component.html',
  styleUrls: ['./interview-calendar.component.css']
})
export class InterviewCalendarComponent implements OnInit {
  calendarOptions: CalendarOptions = {
    plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
    initialView: 'timeGridWeek',
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay'
    },
    events: [],
    editable: false,
    selectable: true,
    eventColor: '#378006', // Optional: set a default event color
    eventTextColor: '#ffffff', // Optional: set event text color
    eventDisplay: 'block', // Optional: how events are displayed
    height: 'auto', // or set a specific height like '600px'
    nowIndicator: true, // shows a marker for current time
    slotMinTime: '08:00:00', // calendar starts at 8am
    slotMaxTime: '20:00:00', // calendar ends at 8pm
    eventClick: this.handleEventClick.bind(this),
    dateClick: this.handleDateClick.bind(this)
  };

  constructor(private interviewService: InterviewService) {}

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    this.interviewService.afficherInterview().subscribe({
      next: (interviews) => {
        const calendarEvents = interviews.map((interview: any) => ({
          id: interview.id, // assuming there's an id field
          title: `Entretien - ${interview.mode}`,
          start: interview.dateEntretien,
          color: this.getStatusColor(interview.status),
          extendedProps: {
            commentaire: interview.commentaire,
            status: interview.status,
            candidate: interview.candidateName // assuming this exists
          }
        }));
        this.calendarOptions.events = calendarEvents;
      },
      error: (err) => {
        console.error('Error loading interviews:', err);
      }
    });
  }

  private getStatusColor(status: string): string {
    // Customize colors based on interview status
    switch(status.toLowerCase()) {
      case 'confirmed': return '#378006'; // green
      case 'pending': return '#ffc107'; // yellow
      case 'cancelled': return '#dc3545'; // red
      default: return '#17a2b8'; // blue
    }
  }

  handleEventClick(clickInfo: any): void {
    // Handle when an event is clicked
    const event = clickInfo.event;
    const title = event.title;
    const status = event.extendedProps['status'];
    const comment = event.extendedProps['commentaire'];
    
    alert(`Interview Details:\nTitle: ${title}\nStatus: ${status}\nComments: ${comment}`);
    
    // Alternatively, you could open a modal here
    // this.openInterviewDetailsModal(event);
  }

  handleDateClick(arg: any): void {
    // Handle when a date/time is clicked
    alert('Date clicked: ' + arg.dateStr);
    
    // You could use this to create new interviews:
    // this.openCreateInterviewModal(arg.date);
  }

  // Optional: Refresh the calendar after changes
  refreshCalendar(): void {
    this.loadEvents();
  }
}