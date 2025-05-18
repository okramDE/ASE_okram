import { Component, OnInit } from '@angular/core';
import { ReportService } from '../../../services/report.service';
import { TimeUsageDto } from '../../../models/time-usage';


@Component({
  selector: 'app-time-usage-chart',
  standalone: false,
  templateUrl: './time-usage-chart.component.html'
})
export class TimeUsageChartComponent implements OnInit {
  // Größe des Charts
  view: [number, number] = [700, 400];

  // Controls für Legende und Labels
  showLegend = true;
  showLabels = true;

  // Die Daten, die ins ngx-charts-Pie-Chart wandern
  chartData: { name: string; value: number }[] = [];

  constructor(private reportService: ReportService) {}

  ngOnInit(): void {
    // Beispiel-Datenintervall
    const from = '2025-06-01';
    const to   = '2025-06-30';

    this.reportService.getTimeUsage(from, to).subscribe((data: TimeUsageDto[]) => {
      this.chartData = data.map(d => ({
        name: d.kategorieName,
        value: d.minuten
      }));
    });
  }
}
