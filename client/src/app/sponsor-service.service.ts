import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Sponsor} from './Sponsor'
import * as CryptoJS from 'crypto-js';
import { environment } from 'src/environments/environment';
 
@Injectable({
  providedIn: 'root'
})
export class SponsorService {

  
  constructor(private http: HttpClient) { }

  getSponsors(): Observable<Sponsor[]> {
    return this.http.get<Sponsor[]>('https://localhost:8080/api/sponsors')
  }

  getPics(title): Observable<any> {
    return this.http.get(`https://localhost:8080/api/images?name=${title}`)
  }

  addSponsor(sponsorName, contactName, contactEmail, status, image): any {
    this.http.post<any>('https://localhost:8080/api/add', {
        sponsorname: sponsorName,
        contactname: contactName,
        contactemail: contactEmail, 
        status: status,
        image: image
    }).subscribe(data=>{return data})
  }

  modifySponsor(sponsorData): any {
    this.http.put<any>('https://localhost:8080/api/modify', {
      _id: sponsorData._id,
      sponsorname: sponsorData.sponsorname,
      contactname: sponsorData.contactname, 
      contactemail: sponsorData.contactemail,
      status: sponsorData.status,
      image: sponsorData.image
    }).subscribe(data=>{return data})
  }

  addAction(actionData): any {
    this.http.post<any>("https://localhost:8080/api/addAction", {
      _id: actionData._id,
      actiontype: actionData.actionType,
      actiondate: actionData.actionDate,
      actionuser: actionData.actionUser,
      actiondetails: actionData.actionDetails
    }).subscribe(data=>{return data})
  }

 


}
