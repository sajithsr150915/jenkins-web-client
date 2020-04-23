import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MyFlightsComponent } from './the-jenkins/the-jenkins.component';
import { JenkinsService } from 'src/app/service/jenkins.service';
import { HttpClientModule } from '@angular/common/http';



@NgModule({
  declarations: [
    AppComponent,
    MyFlightsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
	
  ],
  providers: [JenkinsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
