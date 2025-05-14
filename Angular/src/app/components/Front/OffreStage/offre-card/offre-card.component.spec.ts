import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffreCardComponent } from './offre-card.component';

describe('OffreCardComponent', () => {
  let component: OffreCardComponent;
  let fixture: ComponentFixture<OffreCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OffreCardComponent]
    });
    fixture = TestBed.createComponent(OffreCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
