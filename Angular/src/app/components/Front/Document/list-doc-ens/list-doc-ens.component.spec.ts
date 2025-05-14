import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListDocEnsComponent } from './list-doc-ens.component';

describe('ListDocEnsComponent', () => {
  let component: ListDocEnsComponent;
  let fixture: ComponentFixture<ListDocEnsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListDocEnsComponent]
    });
    fixture = TestBed.createComponent(ListDocEnsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
