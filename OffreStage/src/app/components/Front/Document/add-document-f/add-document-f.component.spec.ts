import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDocumentFComponent } from './add-document-f.component';

describe('AddDocumentFComponent', () => {
  let component: AddDocumentFComponent;
  let fixture: ComponentFixture<AddDocumentFComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddDocumentFComponent]
    });
    fixture = TestBed.createComponent(AddDocumentFComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
