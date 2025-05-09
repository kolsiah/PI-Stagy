import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListValFComponent } from './list-val-f.component';

describe('ListValFComponent', () => {
  let component: ListValFComponent;
  let fixture: ComponentFixture<ListValFComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListValFComponent]
    });
    fixture = TestBed.createComponent(ListValFComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
