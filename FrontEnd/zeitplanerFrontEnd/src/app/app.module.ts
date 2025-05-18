// src/app/app.module.ts

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { NgxChartsModule } from '@swimlane/ngx-charts';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';

import { TerminListComponent } from './components/termin/termin-list/termin-list.component';
import { TerminFormComponent } from './components/termin/termin-form/termin-form.component';
import { AufgabeListComponent } from './components/aufgabe/aufgabe-list/aufgabe-list.component';
import { AufgabeFormComponent } from './components/aufgabe/aufgabe-form/aufgabe-form.component';
import { KategorieListComponent } from './components/kategorie/kategorie-list/kategorie-list.component';
import { KategorieFormComponent } from './components/kategorie/kategorie-form/kategorie-form.component';
import { TimeUsageChartComponent } from './components/report/time-usage-chart/time-usage-chart.component';

@NgModule({
  declarations: [
    AppComponent,
    TerminListComponent,
    TerminFormComponent,
    AufgabeListComponent,
    AufgabeFormComponent,
    KategorieListComponent,
    KategorieFormComponent,
    TimeUsageChartComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgxChartsModule,
    AppRoutingModule     // ← stellt Router-Directives wie <router-outlet> bereit
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
