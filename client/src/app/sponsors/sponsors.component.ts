import { Component, OnInit } from '@angular/core';
import {SponsorService} from '../sponsor-service.service';
import {Sponsor} from '../Sponsor';
import {DomSanitizer} from '@angular/platform-browser'

//Handle sponsor display and input to update sponsor actions.
@Component({
  selector: 'app-sponsors',
  templateUrl: './sponsors.component.html',
  styleUrls: ['./sponsors.component.css']
})
export class SponsorsComponent implements OnInit {
  sponsors: Sponsor[]
  images: Object = {}
  selected: string
  emails: Object={}

  constructor(private sponsorService: SponsorService,
    private sanitizer: DomSanitizer) { }
  
  //Get sponsors on load.
  ngOnInit(): void {
    this.getHeroes()
  }

  //Handle click on "Add Action"
  onClick(event): void {
    this.selected = event.target.id
  }

  //Get sponsors
  getHeroes(): void {
      this.sponsorService.getSponsors().subscribe(sponsors => {
        this.sponsors=sponsors
        console.log(sponsors)
        this.getPics()
      })
  }

  //Get list of emails from API
  getMail(keyword): void {
    this.sponsorService.getMail(keyword).subscribe(mail => {
      this.emails[keyword] = Object.entries(mail).slice(0, 5)
      console.log(keyword)
      console.log(mail)
    })
  }

  //Get sponsor logos
  getPics(): void {
    
    this.sponsors.forEach(sponsor => {
      //If sponsor already has an image, add to images array for display
      if (sponsor.image)
      {
        this.images[sponsor.sponsorname] = sponsor.image
      }
      //If sponsor doesn't have an image, get an image from the google images API, and save it to the sponsor's database entry.
      else {
        this.sponsorService.getPics(sponsor.sponsorname).subscribe(res=>{
          this.images[sponsor.sponsorname]=res['items'][0]['image']['thumbnailLink']
          sponsor.image = res['items'][0]['image']['thumbnailLink']
          this.sponsorService.modifySponsor(sponsor)
       })
      }
      //Get emails after sponsor logos are loaded.
      this.getMail(sponsor.sponsorname)
    
     })

   
  }

  

}
