import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap'
import { ServiceAwakeService } from './service-awake.service'
import { LoadingModalComponent } from './loading-modal/loading-modal.component'
import { Observable } from 'rxjs';

//Root component
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title = 'client';
  isLoaded: boolean = false;

  displayModal = this.awakeService.displayLoadModal.subscribe(displayState => {
    if (displayState) {
      this.modalService.open(LoadingModalComponent);
    }
    else {
      this.modalService.dismissAll()
    }
  });

  constructor(private awakeService: ServiceAwakeService, private modalService: NgbModal) { 
    
  }

  ngOnInit() {
    this.awakeService.wakeServices().then(_ => this.isLoaded = true)  
  }

}
