import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Sponsor} from './Sponsor'
import { environment } from 'src/environments/environment';
 
//Service providing all API methods to access and update sponsors, get images and get mail.
@Injectable({
  providedIn: 'root'
})
export class ApiService {

  
  constructor(private http: HttpClient) { }

  async wakeupServices(): Promise<boolean> {
    return this.http.get<boolean>(`${environment.api_base}/wake/`, {headers: new HttpHeaders({'X-No-Intercept': 'true'})}).toPromise()
  }

  //Get sponsors from API
  getSponsors(params): Observable<Sponsor[]> {
    return this.http.get<Sponsor[]>(`${environment.api_base}/sponsors/`, {params: params})
  }

  getSponsorNum(params): Observable<Number> {
    return this.http.get<Number>(`${environment.api_base}/sponsors/numsponsors/`, {params: params})
  }

  //Get pictures from google images API by proxying through app API.
  getPics(title): Observable<any> {
    return this.http.get(`${environment.api_base}/images?name=${title}/`)
  }

  //Get mail from API, pass in email and password (currently from environment file)
  getMail(key): Observable<any> {
    return this.http.post(`${environment.api_base}/mail/`, {
      email: environment.email_user,
      pass: environment.email_pass, 
      keyword: key
    })
  }

  addSponsor(sponsorName, contactName, contactEmail, status, image): any {
    this.http.post<any>(`${environment.api_base}/sponsors/add/`, {
        _sponsorname: sponsorName,
        _contactname: contactName,
        _contactemail: contactEmail, 
        _status: status,
        _image: image
    }).subscribe(data=>{return data})
  }

  modifySponsor(sponsorData): any {
    this.http.put<any>(`${environment.api_base}/sponsors/modify/`, {
      _id: sponsorData._id,
      _sponsorname: sponsorData._sponsorname,
      _contactname: sponsorData._contactname, 
      _contactemail: sponsorData._contactemail,
      _status: sponsorData._status,
      _image: sponsorData._image
    }).subscribe(data=>{return data})
  }

  addAction(actionData): any {
    this.http.post<any>(`${environment.api_base}/sponsors/addAction/`, {
      _id: actionData._id,
      _actiontype: actionData._actionType,
      _actiondate: actionData._actionDate,
      _actionuser: actionData._actionUser,
      _actiondetails: actionData._actionDetails,
      _netfinancialchange: actionData._netfinancialchange
    }).subscribe(data=>{return data})
  }

 


}
