import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeUsageChartComponent } from './time-usage-chart.component';

describe('TimeUsageChartComponent', () => {
  let component: TimeUsageChartComponent;
  let fixture: ComponentFixture<TimeUsageChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TimeUsageChartComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TimeUsageChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
