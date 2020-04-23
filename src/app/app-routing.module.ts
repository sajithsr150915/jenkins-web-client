import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MyFlightsComponent } from './the-jenkins/the-jenkins.component';

const routes: Routes = [
  { path:'', component: MyFlightsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
