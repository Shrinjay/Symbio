import { Component, OnInit } from '@angular/core';
import {SponsorService} from '../sponsor-service.service';
import {Sponsor} from '../Sponsor';
import {GlobalToggleService} from '../global-toggle.service';

@Component({
  selector: 'app-project-view',
  templateUrl: './project-view.component.html',
  styleUrls: ['./project-view.component.css']
})
export class ProjectViewComponent implements OnInit {

  constructor(private sponsorService: SponsorService, private toggleService: GlobalToggleService) { }

  projects: Sponsor[]
  statusLookup: Object = {
    'Identified': 'badge-danger', 
    'Contacted': 'badge-warning', 
    'Negotiating': 'badge-success'
  }
  selectedAction: Object

  ngOnInit(): void {
  this.getProjects({_status: ["Negotiating", "Contacted", "Identified"]})
  }

  getProjects(params)
  {
    this.sponsorService.getSponsors(params).subscribe(projects => this.projects=projects)
  }

  onClick(event)
  {
    this.toggleService.updateSelected(event.currentTarget.id)
  }

  onSelect(action)
  {
    this.selectedAction = action
    console.log((this.selectedAction)?true:false)
  }
  

}
