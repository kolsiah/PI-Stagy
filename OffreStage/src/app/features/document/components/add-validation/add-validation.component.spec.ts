import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddValidationComponent } from './add-validation.component';

describe('AddValidationComponent', () => {
  let component: AddValidationComponent;
  let fixture: ComponentFixture<AddValidationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddValidationComponent]
    });
    fixture = TestBed.createComponent(AddValidationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
