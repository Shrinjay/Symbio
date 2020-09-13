import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Sponsor} from './Sponsor'
@Injectable({
  providedIn: 'root'
})
export class SponsorService {

  constructor(private http: HttpClient) { }

  getSponsors(): Observable<Sponsor[]> {
    return this.http.get<Sponsor[]>('http://localhost:8080/api/sponsors')
  }

  getPics(title): Observable<any> {
    return this.http.get(`http://localhost:8080/api/images?name=${title}`)
  }

  addSponsor(sponsorName, contactName, contactEmail, status, image): any {
    this.http.post<any>('http://localhost:8080/api/add', {
        sponsorName: sponsorName,
        contactName: contactName,
        contactEmail: contactEmail, 
        status: status,
        image: image
    }).subscribe(data=>{return data})
  }

  modifySponsor(sponsorData): any {
    this.http.put<any>('http://localhost:8080/api/modify', {
      _id: sponsorData._id,
      sponsorName: sponsorData.sponsorName,
      contactName: sponsorData.contactName, 
      contactEmail: sponsorData.contactEmail,
      status: sponsorData.status,
      image: sponsorData.image
    }).subscribe(data=>{return data})
  }

  addAction(actionData): any {
    this.http.post<any>("http://localhost:8080/api/addAction", {
      _id: actionData._id,
      actionType: actionData.actionType,
      actionDate: actionData.actionDate,
      actionUser: actionData.actionUser,
      actionDetails: actionData.actionDetails
    }).subscribe(data=>{return data})
  }


}
