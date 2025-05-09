import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffreButtonsComponent } from './offre-buttons.component';

describe('OffreButtonsComponent', () => {
  let component: OffreButtonsComponent;
  let fixture: ComponentFixture<OffreButtonsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OffreButtonsComponent]
    });
    fixture = TestBed.createComponent(OffreButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
