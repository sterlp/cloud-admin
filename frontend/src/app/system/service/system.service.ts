import { Injectable } from '@angular/core';
import { SystemCredential, SystemCredentialHateoasList } from '../api/system-api.model';
import { HttpClient } from '@angular/common/http';
import { SpringResourece } from '@sterlp/ng-spring-boot-api';

@Injectable({
  providedIn: 'root'
})
export class SystemService extends SpringResourece<SystemCredentialHateoasList, SystemCredential> {
    constructor(http: HttpClient) { super(http); }
    get listUrl(): string { return '/api/system-credentials'; }
}
