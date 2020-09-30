import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import {SponsorService} from '../sponsor-service.service';
import { Sponsor } from '../Sponsor';

//Handle form logic and API request to add a new sponsor.
@Component({
  selector: 'app-new-sponsor',
  templateUrl: './new-sponsor.component.html',
  styleUrls: ['./new-sponsor.component.css']
})
export class NewSponsorComponent implements OnInit {

  newSponsor;

  constructor(private formBuilder: FormBuilder,
    private sponsorService: SponsorService) { 
    this.newSponsor = this.formBuilder.group({
      sponsorName: null, 
      contactName: null,
      contactEmail: null,
      status: null,
      image: null
    })
  }

  //Handle submission through sponsorService
  onSubmit(data) {
    this.sponsorService.addSponsor(data.sponsorName, data.contactName, data.contactEmail, data.status, data.image)
    .then(()=>{this.newSponsor.reset()
      window.location.reload(); })
  }

  //Read file upload into byte-64 image string
  onFileChange(event) {
    const reader = new FileReader()

    if (event.target.files && event.target.files.length)
    { 
      const file = event.target.files[0]
      reader.readAsDataURL(file)
      reader.onload = () => {
        this.newSponsor.patchValue({
          image: reader.result
        })
      }
      
    }
  }
  

  ngOnInit(): void {
  }

}
