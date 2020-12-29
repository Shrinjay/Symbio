import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import {FormsModule} from '@angular/forms';
import { ReactiveFormsModule} from '@angular/forms';
import { AppComponent } from './app.component';
import { SponsorsComponent } from './sponsors/sponsors.component';
import { NewSponsorComponent } from './new-sponsor/new-sponsor.component';
import { NewActionComponent } from './new-action/new-action.component';
import { StatsViewComponent } from './stats-view/stats-view.component';
import { ProjectViewComponent } from './project-view/project-view.component';
import { ActionDetailsComponent } from './action-details/action-details.component';

@NgModule({
  declarations: [
    AppComponent,
    SponsorsComponent,
    NewSponsorComponent,
    NewActionComponent,
    StatsViewComponent,
    ProjectViewComponent,
    ActionDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
