import { Injectable, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import {ApiService} from './api-service.service'

const HOST_SERVICE_TIMEOUT = 3000000;

@Injectable({
  providedIn: 'root'
})
export class ServiceAwakeService {

  servicesAwake: Subject<boolean> = new Subject();
  displayLoadModal: Subject<boolean> = new Subject();

  constructor(private apiService: ApiService) {
    this.servicesAwake.next(false);
    this.displayLoadModal.next(true);
  }

  serviceSleepTimer() {
    return new Promise(resolve => setTimeout(resolve, HOST_SERVICE_TIMEOUT))
  }

  async wakeServices() {
    let awokeServices = false;

    this.displayLoadModal.next(true)

    while(!awokeServices) {
      awokeServices = await this.apiService.wakeupServices();
    }
    
    this.displayLoadModal.next(false);
    this.servicesAwake.next(true);

    this.serviceSleepTimer().then(_ => {
      this.servicesAwake.next(false)
    })
  }


}
