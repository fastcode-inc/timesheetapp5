import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IStatus } from './istatus';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class StatusService extends GenericApiService<IStatus> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'status');
  }
}
