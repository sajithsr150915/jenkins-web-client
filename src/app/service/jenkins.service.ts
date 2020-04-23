import { Injectable } from '@angular/core';
import { Jenkins } from "../model/jenkins";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from "rxjs";

@Injectable()
export class JenkinsService{
  
 /* private MYFLIGHTS: Jenkins[] = [
    {"flightNumber" : "FS1298", "origin": "LAX", "destination" : "LHR"},
    {"flightNumber" : "FS1201", "origin": "LAX", "destination" : "LHR"},
  ];*/

  constructor(private http: HttpClient) { }

  // to test with local mock data
  /*public getFlightsMockData() : Jenkins[]{
    return this.MYFLIGHTS;
  }*/





  // count data grouped by jenkins job
  public getJenkinsDataGroup() : Observable<Jenkins[]>{
	  let url = "http://localhost:8083/jenkinsJob/countGroupByJenkinsJob";
	this.http.get(url);	
	return this.http.get<Jenkins[]>(url);
}

  // count data of jenkins job
 public totalBuildCountJenkins() : Observable<Jenkins>{
	  let url = "http://localhost:8083/jenkinsJob/totalBuildCountJenkins";
	this.http.get(url);	
	return this.http.get<Jenkins>(url);
}




public totalBuildCountPROD() : Observable<Jenkins>{
	  let url = "http://localhost:8083/jenkinsJob/totalBuildCountPROD";
	this.http.get(url);	
	return this.http.get<Jenkins>(url);
}

public totalBuildCountUAT() : Observable<Jenkins>{
	  let url = "http://localhost:8083/jenkinsJob/totalBuildCountUAT";
	this.http.get(url);	
	return this.http.get<Jenkins>(url);
}



public lastPRODDeployments() : Observable<Jenkins[]>{
	  let url = "http://localhost:8083/jenkinsJob/lastPRODDeployments";
	this.http.get(url);	
	return this.http.get<Jenkins[]>(url);
}

 public lastUATDeployments() : Observable<Jenkins[]>{
	  let url = "http://localhost:8083/jenkinsJob/lastUATDeployments";
	this.http.get(url);	
	return this.http.get<Jenkins[]>(url);
} 

 public totalCountAcceptance() : Observable<Jenkins>{
	  let url = "http://localhost:8083/jenkinsJob/totalCountAcceptance";
	this.http.get(url);	
	return this.http.get<Jenkins>(url);
} 

 public countGroupByAcceptancTest() : Observable<Jenkins[]>{
	  let url = "http://localhost:8083/jenkinsJob/countGroupByAcceptancTest";
	this.http.get(url);	
	return this.http.get<Jenkins[]>(url);
} 
  
  
 


}