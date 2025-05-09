import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListDocFComponent } from './list-doc-f.component';

describe('ListDocFComponent', () => {
  let component: ListDocFComponent;
  let fixture: ComponentFixture<ListDocFComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListDocFComponent]
    });
    fixture = TestBed.createComponent(ListDocFComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
