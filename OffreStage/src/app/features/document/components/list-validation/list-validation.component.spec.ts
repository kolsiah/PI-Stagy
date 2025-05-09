import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListValidationComponent } from './list-validation.component';

describe('ListValidationComponent', () => {
  let component: ListValidationComponent;
  let fixture: ComponentFixture<ListValidationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListValidationComponent]
    });
    fixture = TestBed.createComponent(ListValidationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
