import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TerminFormComponent } from './termin-form.component';

describe('TerminFormComponent', () => {
  let component: TerminFormComponent;
  let fixture: ComponentFixture<TerminFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TerminFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TerminFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
