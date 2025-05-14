import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffreListComponent } from './offre-list.component';

describe('OffreListComponent', () => {
  let component: OffreListComponent;
  let fixture: ComponentFixture<OffreListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OffreListComponent]
    });
    fixture = TestBed.createComponent(OffreListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
