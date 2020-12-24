import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Sponsor} from './Sponsor'
import * as CryptoJS from 'crypto-js';
import { environment } from 'src/environments/environment';
 
//Service providing all API methods to access and update sponsors, get images and get mail.
@Injectable({
  providedIn: 'root'
})
export class SponsorService {

  
  constructor(private http: HttpClient) { }

  //Get sponsors from API
  getSponsors(params): Observable<Sponsor[]> {
    return this.http.get<Sponsor[]>('https://localhost:8080/api/sponsors', {params: params})
  }

  //Get pictures from google images API by proxying through app API.
  getPics(title): Observable<any> {
    return this.http.get(`https://localhost:8080/api/images?name=${title}`)
  }

  //Get mail from API, pass in email and password (currently from environment file)
  getMail(key): Observable<any> {
    return this.http.post('https://localhost:8080/api/mail', {
      email: environment.user,
      pass: environment.pass, 
      keyword: key
    })
  }

  addSponsor(sponsorName, contactName, contactEmail, status, image): any {
    this.http.post<any>('https://localhost:8080/api/add', {
        _sponsorname: sponsorName,
        _contactname: contactName,
        _contactemail: contactEmail, 
        _status: status,
        _image: image
    }).subscribe(data=>{return data})
  }

  modifySponsor(sponsorData): any {
    this.http.put<any>('https://localhost:8080/api/modify', {
      _id: sponsorData._id,
      _sponsorname: sponsorData._sponsorname,
      _contactname: sponsorData._contactname, 
      _contactemail: sponsorData._contactemail,
      _status: sponsorData._status,
      _image: sponsorData._image
    }).subscribe(data=>{return data})
  }

  addAction(actionData): any {
    this.http.post<any>("https://localhost:8080/api/addAction", {
      _id: actionData._id,
      _actiontype: actionData._actionType,
      _actiondate: actionData._actionDate,
      _actionuser: actionData._actionUser,
      _actiondetails: actionData._actionDetails
    }).subscribe(data=>{return data})
  }

 


}
