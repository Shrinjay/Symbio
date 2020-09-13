import { Component, OnInit } from '@angular/core';
import {SponsorService} from '../sponsor-service.service';
import {Sponsor} from '../Sponsor';
import {DomSanitizer} from '@angular/platform-browser'
@Component({
  selector: 'app-sponsors',
  templateUrl: './sponsors.component.html',
  styleUrls: ['./sponsors.component.css']
})
export class SponsorsComponent implements OnInit {
  sponsors: Sponsor[]
  images: Object = {}
  selected: string

  constructor(private sponsorService: SponsorService,
    private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.getHeroes()
  }

  onClick(event): void {
    this.selected = event.target.id
  }

  getHeroes(): void {
      this.sponsorService.getSponsors().subscribe(sponsors => {
        this.sponsors=sponsors

        this.getPics()
      })
  }

  getPics(): void {
    
    this.sponsors.forEach(sponsor => {
      if (sponsor.image)
      {
        this.images[sponsor.sponsorName] = sponsor.image
      }
      else {
    
        this.sponsorService.getPics(sponsor.sponsorName).subscribe(res=>{
          this.images[sponsor.sponsorName]=res['items'][0]['image']['thumbnailLink']
          sponsor.image = res['items'][0]['image']['thumbnailLink']
          this.sponsorService.modifySponsor(sponsor)
       })
      }
     })

   
  }

  

}
