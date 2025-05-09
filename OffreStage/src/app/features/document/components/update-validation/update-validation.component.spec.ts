import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateValidationComponent } from './update-validation.component';

describe('UpdateValidationComponent', () => {
  let component: UpdateValidationComponent;
  let fixture: ComponentFixture<UpdateValidationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateValidationComponent]
    });
    fixture = TestBed.createComponent(UpdateValidationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
