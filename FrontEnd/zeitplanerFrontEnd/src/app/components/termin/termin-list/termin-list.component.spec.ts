import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TerminListComponent } from './termin-list.component';

describe('TerminListComponent', () => {
  let component: TerminListComponent;
  let fixture: ComponentFixture<TerminListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TerminListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TerminListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
