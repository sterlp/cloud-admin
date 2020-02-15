import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IdentityList, Identity } from '../api/identity-api.model';
import { SpringResourece,
    HateoasEntityList,
    HateoasResourceLinks,
    DataSourceErrorHandler,
    SpringDataSource } from '@sterlp/ng-spring-boot-api';

@Injectable({
  providedIn: 'root'
})
export class IdentityService
    extends SpringResourece<HateoasEntityList<IdentityList, HateoasResourceLinks>, Identity> {
    constructor(http: HttpClient) { super(http); }
    get listUrl() { return '/api/identities'; }
}

export class IdentityDataSource extends SpringDataSource<HateoasEntityList<IdentityList, HateoasResourceLinks>, Identity> {
    constructor(identityService: IdentityService, errorHandler?: DataSourceErrorHandler) {
        super(identityService, errorHandler);
    }
    extractDataFromList(list: HateoasEntityList<IdentityList, HateoasResourceLinks>): Identity[] {
        return list && list._embedded && list._embedded.identities ? list._embedded.identities : null;
    }
}
