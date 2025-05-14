import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListOffreStudentComponent } from './list-offre-student.component';

describe('ListOffreStudentComponent', () => {
  let component: ListOffreStudentComponent;
  let fixture: ComponentFixture<ListOffreStudentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListOffreStudentComponent]
    });
    fixture = TestBed.createComponent(ListOffreStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
