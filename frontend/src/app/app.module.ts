import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './components/app/app.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {ChartsModule} from 'ng2-charts';
import {HostsService} from './services/hosts/hosts.service';
import {ActivitiesService} from './services/activities/activities.service';
import {HomeComponent} from './components/home/home.component';
import {HostsComponent} from './components/hosts/hosts.component';
import { HostComponent } from './components/host/host.component';
import { BasicStatsComponent } from './components/basic-stats/basic-stats.component';
import { BasicActivityComponent } from './components/basic-activity/basic-activity.component';
import { ActivityChartComponent } from './components/activity-chart/activity-chart.component';

const appRoutes: Routes = [
  {path: '', component: HomeComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    HostsComponent,
    HostComponent,
    BasicStatsComponent,
    BasicActivityComponent,
    ActivityChartComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    CommonModule,
    FormsModule,
    HttpClientModule,
    ChartsModule
  ],
  providers: [ActivitiesService, HostsService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
