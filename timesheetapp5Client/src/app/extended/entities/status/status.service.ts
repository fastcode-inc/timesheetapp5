import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { StatusService } from 'src/app/entities/status/status.service';
@Injectable({
  providedIn: 'root',
})
export class StatusExtendedService extends StatusService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
