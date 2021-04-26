import {FormBuilder} from '@angular/forms'
import { Component, OnInit, Input } from '@angular/core';
import {ApiService} from '../api-service.service';
import {GlobalToggleService} from '../global-toggle.service'
declare var $: any;

//Handle form input and API request for adding new actions
@Component({
  selector: 'app-new-action',
  templateUrl: './new-action.component.html',
  styleUrls: ['./new-action.component.css']
})
export class NewActionComponent implements OnInit {

  @Input() selected: String

  newAction

  constructor(private apiService: ApiService,
    private formBuilder: FormBuilder, private toggleService:  GlobalToggleService) { 
      this.newAction = this.formBuilder.group({
          _actionType: null,
          _actionDate: null, 
          _actionUser: null, 
          _actionDetails: null,
          _netfinancialchange: null
      })
    }

  onSubmit(data) {
    if (data._actionType=="Payment TO") data._netfinancialchange *= -1
    let actionData = {
      _id: this.selected,
      _actionType: data._actionType,
      _actionDate: data._actionDate,
      _actionUser: data._actionUser,
      _actionDetails: data._actionDetails,
      _netfinancialchange: data._netfinancialchange
    }
    this.apiService.addAction(actionData)
  }

  ngOnInit(): void {
    console.log(this.selected)
    $('#actionModal').modal('show');
  }
  
  ngOnChanges() {
    $('#actionModal').modal('show');
  }

  closeModal()
  {
    this.toggleService.updateSelected("")
  }

}
