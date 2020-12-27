import { Component, OnInit, Input } from '@angular/core';
declare var $: any;
@Component({
  selector: 'app-action-details',
  templateUrl: './action-details.component.html',
  styleUrls: ['./action-details.component.css']
})
export class ActionDetailsComponent implements OnInit {

  @Input() action: Object
 
  constructor() { }

  ngOnInit(): void {
    console.log("running: ")
    $('#actionModal').modal('show');
  }

  ngOnChanges() {
    $('#actionModal').modal('show');
  }

  closeModal() {
    this.action={}
  }

}
