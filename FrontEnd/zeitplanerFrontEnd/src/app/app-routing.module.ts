import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Komponenten importieren
import { TerminListComponent } from './components/termin/termin-list/termin-list.component';
import { TerminFormComponent } from './components/termin/termin-form/termin-form.component';
import { AufgabeListComponent } from './components/aufgabe/aufgabe-list/aufgabe-list.component';
import { AufgabeFormComponent } from './components/aufgabe/aufgabe-form/aufgabe-form.component';
import { KategorieListComponent } from './components/kategorie/kategorie-list/kategorie-list.component';
import { KategorieFormComponent } from './components/kategorie/kategorie-form/kategorie-form.component';
import { TimeUsageChartComponent } from './components/report/time-usage-chart/time-usage-chart.component';

const routes: Routes = [
  // Termine
  { path: 'termine',         component: TerminListComponent },
  { path: 'termine/new',     component: TerminFormComponent },
  { path: 'termine/edit/:id',component: TerminFormComponent },

  // Aufgaben
  { path: 'aufgaben',         component: AufgabeListComponent },
  { path: 'aufgaben/new',     component: AufgabeFormComponent },
  { path: 'aufgaben/edit/:id',component: AufgabeFormComponent },

  // Kategorien
  { path: 'kategorien',         component: KategorieListComponent },
  { path: 'kategorien/new',     component: KategorieFormComponent },
  { path: 'kategorien/edit/:id',component: KategorieFormComponent },

  // Report
  { path: 'report/time-usage', component: TimeUsageChartComponent },

  // Default
  { path: '', redirectTo: 'termine', pathMatch: 'full' },
  { path: '**', redirectTo: 'termine' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
