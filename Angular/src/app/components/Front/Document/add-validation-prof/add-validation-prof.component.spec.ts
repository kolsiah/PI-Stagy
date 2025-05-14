import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddValidationProfComponent } from './add-validation-prof.component';

describe('AddValidationProfComponent', () => {
  let component: AddValidationProfComponent;
  let fixture: ComponentFixture<AddValidationProfComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddValidationProfComponent]
    });
    fixture = TestBed.createComponent(AddValidationProfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
