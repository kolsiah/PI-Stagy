import { Component, OnInit } from '@angular/core';
import { Document } from '../Model/DocumentMod';
import { DocumentService } from '../Services/document.service';

@Component({
  selector: 'app-list-document',
  templateUrl: './list-document.component.html',
  styleUrls: ['./list-document.component.scss']
})
export class ListDocumentComponent implements OnInit{
ListDoc : Document[] = [];

  constructor(private LD  : DocumentService) { }
  ngOnInit(): void {
    this.LD.getDocument().subscribe(data => this.ListDoc = data);
  }
  deleteDOC(idDocument : number): void{
    if (confirm('Are you sure you want to delete this document?')) {
      this.LD.delDocument(idDocument).subscribe({
        next: () => {
          // Remove the document from the list after deletion
          this.ListDoc = this.ListDoc.filter(doc => doc.idDocument !== idDocument);
          alert('Document deleted successfully');
        },
        error: (err) => {
          console.error('Error deleting document:', err);
          alert('Failed to delete document');
        }
      });
    }
  }
}
