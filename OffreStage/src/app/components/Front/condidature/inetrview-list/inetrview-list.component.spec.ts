import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InetrviewListComponent } from './inetrview-list.component';

describe('InetrviewListComponent', () => {
  let component: InetrviewListComponent;
  let fixture: ComponentFixture<InetrviewListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InetrviewListComponent]
    });
    fixture = TestBed.createComponent(InetrviewListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
