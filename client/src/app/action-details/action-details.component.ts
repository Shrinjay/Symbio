import { Component, OnInit, Input } from '@angular/core';
import {GlobalToggleService} from '../global-toggle.service'
declare var $: any;
@Component({
  selector: 'app-action-details',
  templateUrl: './action-details.component.html',
  styleUrls: ['./action-details.component.css']
})
export class ActionDetailsComponent implements OnInit {

  @Input() action: Object = this.toggleService.selected_action_details.subscribe(action => this.action = action)
 
  constructor(private toggleService: GlobalToggleService) { }

  ngOnInit(): void {
    console.log("running: ")
    $('#actionModal').modal('show');
  }

  ngOnChanges() {
    $('#actionModal').modal('show');
  }

  closeModal() {
    this.toggleService.updateDetails("")
  }

}
