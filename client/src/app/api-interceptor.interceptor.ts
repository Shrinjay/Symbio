import { Injectable } from '@angular/core';
import {ServiceAwakeService} from './service-awake.service'
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class ApiInterceptorInterceptor implements HttpInterceptor {

  serviceState: boolean = true;
  serviceSubscription = this.awakenService.servicesAwake.subscribe(state => {
    this.serviceState = state
  });

  constructor(private awakenService: ServiceAwakeService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (request.headers && !request.headers.has('X-No-Intercept') && !this.serviceState) {
      this.awakenService.wakeServices()
    }

    return next.handle(request);
  }
}
