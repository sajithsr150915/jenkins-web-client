import { Component, OnInit } from '@angular/core';

import {Jenkins} from '../model/jenkins';
import { JenkinsService } from 'src/app/service/jenkins.service';

@Component({
  selector: 'app-the-jenkins',
  templateUrl: './the-jenkins.component.html',
  styleUrls: ['./the-jenkins.component.scss']
})
export class MyFlightsComponent implements OnInit {
  private jenkinsJob : Jenkins[];
    private jenkinsCount : Jenkins;
    private jenkinsCountPROD : Jenkins;
    private jenkinsCountUAT : Jenkins;
	private lastPRODDeploy : Jenkins[];
	private lastUATDeploy : Jenkins[];
	private  acceptanceCount : Jenkins;
    private  acceptanceCountGroup : Jenkins[];
 

  constructor(private jenkinsService: JenkinsService) {}

  ngOnInit() {
    //this.flights = this.flightService.getFlightsMockData();
    this.getJenkinsDataGroup();
	this.totalBuildCountJenkins();
	this.totalBuildCountPROD();
	this.totalBuildCountUAT();
	this.lastPRODDeployments();
	this.lastUATDeployments();
	this.totalCountAcceptance();
	this.countGroupByAcceptancTest();


  }

  // get the data from backend service api
  private getJenkinsDataGroup() {
    this.jenkinsService.getJenkinsDataGroup().subscribe(data => {
      this.jenkinsJob = data;
    });
  }
  
    private totalBuildCountJenkins() {
    this.jenkinsService.totalBuildCountJenkins().subscribe(data => {
      this.jenkinsCount = data;
    });
  }
  
  
  
    private totalBuildCountPROD() {
    this.jenkinsService.totalBuildCountPROD().subscribe(data => {
      this.jenkinsCountPROD = data;
    });
  }
  
   private totalBuildCountUAT() {
    this.jenkinsService.totalBuildCountUAT().subscribe(data => {
      this.jenkinsCountUAT = data;
    });
  }
  
   private lastPRODDeployments() {
    this.jenkinsService.lastPRODDeployments().subscribe(data => {
      this.lastPRODDeploy = data;
    });
  }
  
  
     private lastUATDeployments() {
    this.jenkinsService.lastUATDeployments().subscribe(data => {
      this.lastUATDeploy = data;
    });
  }
  
  private totalCountAcceptance() {
    this.jenkinsService.totalCountAcceptance().subscribe(data => {
      this.acceptanceCount = data;
    });
  }
  
   private countGroupByAcceptancTest() {
    this.jenkinsService.countGroupByAcceptancTest().subscribe(data => {
      this.acceptanceCountGroup = data;
    });
  }
  
}
