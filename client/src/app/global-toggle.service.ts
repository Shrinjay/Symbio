import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Subject} from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class GlobalToggleService {

  public selected_new_action = new Subject<string>()
  public selected_action_details = new Subject<string>()

  updateSelected(newSelected)
  {
    this.selected_new_action.next(newSelected)
  }

  updateDetails(newDetails)
  {
    this.selected_action_details.next(newDetails)
  }

  

}
