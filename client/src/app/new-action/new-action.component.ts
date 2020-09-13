import {FormBuilder} from '@angular/forms'
import { Component, OnInit, Input } from '@angular/core';
import {SponsorService} from '../sponsor-service.service';
declare var $: any;

@Component({
  selector: 'app-new-action',
  templateUrl: './new-action.component.html',
  styleUrls: ['./new-action.component.css']
})
export class NewActionComponent implements OnInit {

  @Input() selected: String

  newAction

  constructor(private sponsorService: SponsorService,
    private formBuilder: FormBuilder ) { 
      this.newAction = this.formBuilder.group({
          actionType: null,
          actionDate: null, 
          actionUser: null, 
          actionDetails: null
      })
    }

  onSubmit(data) {
    let actionData = {
      _id: this.selected,
      actionType: data.actionType,
      actionDate: data.actionDate,
      actionUser: data.actionUser,
      actionDetails: data.actionDetails
      
    }
    this.sponsorService.addAction(actionData)
  }

  ngOnInit(): void {
    console.log(this.selected)
    $('#actionModal').modal('show');
  }
  
  ngOnChanges() {
    $('#actionModal').modal('show');
  }

}
