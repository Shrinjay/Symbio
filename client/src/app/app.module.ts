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

@NgModule({
  declarations: [
    AppComponent,
    SponsorsComponent,
    NewSponsorComponent,
    NewActionComponent
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
