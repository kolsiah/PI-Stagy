import { Component, OnInit } from '@angular/core';
import { Document } from 'src/app/features/document/Model/DocumentMod';
import { DocumentService } from 'src/app/features/document/services/document.service';

@Component({
  selector: 'app-list-document',
  templateUrl: './list-document.component.html',
  styleUrls: ['./list-document.component.scss']
})
export class ListDocumentComponent implements OnInit{
ListDoc : Document[] = [];
selectedCriteria: string = 'studentId'; // default search by studentId
searchValue: string = ''; // input value
sortedDocuments : Document[]=[]; // Array to hold the sorted documents
sortAsc = true; // Flag to toggle between ascending and descending order
  constructor(private LD  : DocumentService) { }
  ngOnInit(): void {
    this.LD.getDocument().subscribe(data => this.ListDoc = data);
    this.LD.getDocument().subscribe(data => this.sortedDocuments = data);
  }
    // Function to handle sorting
    sortDocuments(): void {
      this.sortAsc = !this.sortAsc; // Toggle sorting order (ascending/descending)
  
      // Sort the documents by date
      this.ListDoc.sort((a, b) => {
        const dateA = new Date(a.dateUpload).getTime();
        const dateB = new Date(b.dateUpload).getTime();
  
        if (this.sortAsc) {
          return dateA - dateB; // Ascending
        } else {
          return dateB - dateA; // Descending
        }
      });
    }
  getFilteredDocuments() {
    return this.ListDoc.filter(doc => {
      // Search by StudentId
      if (this.selectedCriteria === 'studentId' && doc.etudiantId.toString().includes(this.searchValue)) {
        return true;
      }
      // Search by DocumentTitle
      if (this.selectedCriteria === 'documentTitle' && doc.titreDocument.toLowerCase().includes(this.searchValue.toLowerCase())) {
        return true;
      }
      // Search by Statut
      if (this.selectedCriteria === 'statut' && doc.statutDocument.toLowerCase().includes(this.searchValue.toLowerCase())) {
        return true;
      }
      return false;
    });
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
