import { Component, OnInit } from '@angular/core';
import {ApiService} from '../api-service.service';
import {Sponsor} from '../Sponsor';
import {DomSanitizer} from '@angular/platform-browser'
import { HttpParams } from '@angular/common/http';
import {GlobalToggleService} from '../global-toggle.service';
import { Subscription } from 'rxjs';

//Handle sponsor display and input to update sponsor actions.
@Component({
  selector: 'app-sponsors',
  templateUrl: './sponsors.component.html',
  styleUrls: ['./sponsors.component.css']
})
export class SponsorsComponent implements OnInit {
  sponsors: Sponsor[]
  images: Object = {}
  selected: any = this.toggleService.selected_new_action.subscribe(selected=> {
    this.selected = selected
    console.log(this.selected)
  })
  emails: Object={}
  
  constructor(private apiService: ApiService,
    private sanitizer: DomSanitizer, private toggleService: GlobalToggleService) { }
  
  //Get sponsors on load.
  ngOnInit(): void {
    this.getHeroes({_status: "Established"})
    this.toggleService.updateSelected("")
  }

  //Handle click on "Add Action"
  onClick(event): void {
    this.toggleService.updateSelected(event.currentTarget.id)
    console.log(this.selected)
  }

  toggleActions(event): void {
    this.sponsors.find(sponsor =>sponsor._id==event.currentTarget.id).expandActions = !(this.sponsors.find(sponsor => sponsor._id== event.currentTarget.id).expandActions)
    
  }

  //Get sponsors
  getHeroes(params): void {
      this.apiService.getSponsors(params).subscribe(sponsors => {
        sponsors.forEach(sponsor => sponsor.expandActions=false)
        this.sponsors=sponsors
        this.getPics()
      })
  }

  //Get list of emails from API
  getMail(keyword): void {
    this.apiService.getMail(keyword).subscribe(mail => {
      this.emails[keyword] = Object.entries(mail).slice(0, 5)
      console.log(keyword)
      console.log(mail)
    })
  }

  //Get sponsor logos
  getPics(): void {
    
    this.sponsors.forEach(sponsor => {
      //If sponsor already has an image, add to images array for display
      if (sponsor._image)
      {
        this.images[sponsor._sponsorname] = sponsor._image
      }
      //If sponsor doesn't have an image, get an image from the google images API, and save it to the sponsor's database entry.
      else {
        this.apiService.getPics(sponsor._sponsorname).subscribe(res=>{
          this.images[sponsor._sponsorname]=res['items'][0]['image']['thumbnailLink']
          sponsor._image = res['items'][0]['image']['thumbnailLink']
          this.apiService.modifySponsor(sponsor)
       })
      }
      //Get emails after sponsor logos are loaded.
      this.getMail(sponsor._sponsorname)
    
     })

   
  }

  

}
