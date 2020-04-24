import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {Jenkins} from '../model/jenkins';
import { JenkinsService } from 'src/app/service/jenkins.service';
import {NgForm} from '@angular/forms';
//import { NgxSpinnerService } from "ngx-spinner"; 
import { LoaderService } from 'src/app/service/loader.service';

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
	private issueForm: FormGroup;
	private loaderService: LoaderService;
	//private SpinnerService: NgxSpinnerService;

  constructor(private jenkinsService: JenkinsService) {}

  ngOnInit() {
	//this.SpinnerService.show();
	 // this.loaderService.display(true);

    this.getJenkinsDataGroup("");
	this.totalBuildCountJenkins("");
	this.totalBuildCountPROD("");
	this.totalBuildCountUAT("");
	this.lastPRODDeployments("");
	this.lastUATDeployments("");
	this.totalCountAcceptance("");
	this.countGroupByAcceptancTest("");
    //this.SpinnerService.hide(); 
	 // this.loaderService.display(false);

  }


onSubmit(form: NgForm) {
      console.log('Your form data : ', form.value.days);
	  let days=form.value.days;
	  this.getJenkinsDataGroup(days);
	this.totalBuildCountJenkins(days);
	this.totalBuildCountPROD(days);
	this.totalBuildCountUAT(days);
	this.lastPRODDeployments(days);
	this.lastUATDeployments(days);
	this.totalCountAcceptance(days);
	this.countGroupByAcceptancTest(days);
  }
  
  
  // get the data from backend service api
  private getJenkinsDataGroup(days) {
    this.jenkinsService.getJenkinsDataGroup(days).subscribe(data => {
      this.jenkinsJob = data;
    });
  }
  
    private totalBuildCountJenkins(days) {
    this.jenkinsService.totalBuildCountJenkins(days).subscribe(data => {
      this.jenkinsCount = data;
    });
  }
  
  
  
    private totalBuildCountPROD(days) {
    this.jenkinsService.totalBuildCountPROD(days).subscribe(data => {
      this.jenkinsCountPROD = data;
    });
  }
  
   private totalBuildCountUAT(days) {
    this.jenkinsService.totalBuildCountUAT(days).subscribe(data => {
      this.jenkinsCountUAT = data;
    });
  }
  
   private lastPRODDeployments(days) {
    this.jenkinsService.lastPRODDeployments(days).subscribe(data => {
      this.lastPRODDeploy = data;
    });
  }
  
  
     private lastUATDeployments(days) {
    this.jenkinsService.lastUATDeployments(days).subscribe(data => {
      this.lastUATDeploy = data;
    });
  }
  
  private totalCountAcceptance(days) {
    this.jenkinsService.totalCountAcceptance(days).subscribe(data => {
      this.acceptanceCount = data;
    });
  }
  
   private countGroupByAcceptancTest(days) {
    this.jenkinsService.countGroupByAcceptancTest(days).subscribe(data => {
      this.acceptanceCountGroup = data;
    });
  }
  
}
