import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CondidatureComponent } from './condidature.component';

describe('CondidatureComponent', () => {
  let component: CondidatureComponent;
  let fixture: ComponentFixture<CondidatureComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CondidatureComponent]
    });
    fixture = TestBed.createComponent(CondidatureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
