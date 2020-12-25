import { Component, OnInit } from '@angular/core';
import {SponsorService} from '../sponsor-service.service';
import {Sponsor} from '../Sponsor';
@Component({
  selector: 'app-project-view',
  templateUrl: './project-view.component.html',
  styleUrls: ['./project-view.component.css']
})
export class ProjectViewComponent implements OnInit {

  constructor(private sponsorService: SponsorService) { }

  projects: Sponsor[]

  ngOnInit(): void {
  this.getProjects({_status: ["Negotiating", "Contacted", "Identified"]})
  }

  getProjects(params)
  {
    this.sponsorService.getSponsors(params).subscribe(projects => this.projects=projects)
  }

}
