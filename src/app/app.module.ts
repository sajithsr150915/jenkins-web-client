import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MyFlightsComponent } from './the-jenkins/the-jenkins.component';
import { JenkinsService } from 'src/app/service/jenkins.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule,FormGroup} from '@angular/forms';
import { LoaderService } from 'src/app/service/loader.service';



@NgModule({
  declarations: [
    AppComponent,
    MyFlightsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
	FormsModule,
    ReactiveFormsModule
	
  ],
  providers: [JenkinsService,LoaderService],
  bootstrap: [AppComponent]
})
export class AppModule { }
