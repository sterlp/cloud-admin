import { Injectable } from '@angular/core';
import { SpringResoureceService } from 'src/app/shared/public-api';
import { SystemCredential, SystemCredentialHateoasList } from '../api/system-api.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SystemService extends SpringResoureceService<SystemCredentialHateoasList, SystemCredential> {
    constructor(http: HttpClient) { super(http); }
    get listUrl(): string { return 'api/system-credentials'; }
}
