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
  public getJenkinsDataGroup(days) : Observable<Jenkins[]>{
	  let url = "http://localhost:8083/jenkinsJob/countGroupByJenkinsJob";
	  if(days.trim().length===0){
	  }else{
		  		  url=url+"/"+days;

	  }
	  
	this.http.get(url);	
	return this.http.get<Jenkins[]>(url);
}

  // count data of jenkins job
 public totalBuildCountJenkins(days) : Observable<Jenkins>{
	  let url = "http://localhost:8083/jenkinsJob/totalBuildCountJenkins";
	  if(days.trim().length===0){
	  }else{
		  		  url=url+"/"+days;

	  }
	this.http.get(url);	
	return this.http.get<Jenkins>(url);
}




public totalBuildCountPROD(days) : Observable<Jenkins>{
	  let url = "http://localhost:8083/jenkinsJob/totalBuildCountPROD";
	  if(days.trim().length===0){
	  }else{
		  		  url=url+"/"+days;

	  }
	this.http.get(url);	
	return this.http.get<Jenkins>(url);
}

public totalBuildCountUAT(days) : Observable<Jenkins>{
	  let url = "http://localhost:8083/jenkinsJob/totalBuildCountUAT";
	  if(days.trim().length===0){
	  }else{
		  		  url=url+"/"+days;

	  }
	this.http.get(url);	
	return this.http.get<Jenkins>(url);
}



public lastPRODDeployments(days) : Observable<Jenkins[]>{
	  let url = "http://localhost:8083/jenkinsJob/lastPRODDeployments";
	  if(days.trim().length===0){
	  }else{
		  		  url=url+"/"+days;

	  }
	this.http.get(url);	
	return this.http.get<Jenkins[]>(url);
}

 public lastUATDeployments(days) : Observable<Jenkins[]>{
	  let url = "http://localhost:8083/jenkinsJob/lastUATDeployments";
	  if(days.trim().length===0){
	  }else{
		  		  url=url+"/"+days;

	  }
	this.http.get(url);	
	return this.http.get<Jenkins[]>(url);
} 

 public totalCountAcceptance(days) : Observable<Jenkins>{
	  let url = "http://localhost:8083/jenkinsJob/totalCountAcceptance";
	  if(days.trim().length===0){
	  }else{
		  		  url=url+"/"+days;

	  }
	this.http.get(url);	
	return this.http.get<Jenkins>(url);
} 

 public countGroupByAcceptancTest(days) : Observable<Jenkins[]>{
	  let url = "http://localhost:8083/jenkinsJob/countGroupByAcceptancTest";
	  if(days.trim().length===0){
	  }else{
		  		  url=url+"/"+days;

	  }
	this.http.get(url);	
	return this.http.get<Jenkins[]>(url);
} 
  
  
 


}