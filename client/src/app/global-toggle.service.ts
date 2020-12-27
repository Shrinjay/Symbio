import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Subject} from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class GlobalToggleService {

  public selected_new_action = new Subject<string>()

  updateSelected(newSelected)
  {
    this.selected_new_action.next(newSelected)
  }

  

}
