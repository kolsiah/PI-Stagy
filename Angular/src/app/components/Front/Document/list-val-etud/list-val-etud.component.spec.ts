import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListValEtudComponent } from './list-val-etud.component';

describe('ListValEtudComponent', () => {
  let component: ListValEtudComponent;
  let fixture: ComponentFixture<ListValEtudComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListValEtudComponent]
    });
    fixture = TestBed.createComponent(ListValEtudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
