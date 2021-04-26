import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import {ApiService} from '../api-service.service';
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
    private apiService: ApiService) { 
    this.newSponsor = this.formBuilder.group({
      _sponsorName: null, 
      _contactName: null,
      _contactEmail: null,
      _status: null,
      _image: null
    })
  }

  //Handle submission through apiService
  onSubmit(data) {
    this.apiService.addSponsor(data._sponsorName, data._contactName, data._contactEmail, data._status, data._image)
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
