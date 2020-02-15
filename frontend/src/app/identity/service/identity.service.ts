import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Pageable,HateoasEntityList,HateoasResourceLinks, Sort } from '@sterlp/ng-spring-boot-api';
import { Observable, BehaviorSubject, of, Subscription } from 'rxjs';
import { catchError, map, tap, finalize } from 'rxjs/operators';
import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { IdentityList, Identity } from '../api/identity-api.model';
import { SpringResoureceService, SpringDataSource, DataSourceErrorHandler } from 'src/app/shared/public-api';

@Injectable({
  providedIn: 'root'
})
export class IdentityService
    extends SpringResoureceService<HateoasEntityList<IdentityList,HateoasResourceLinks>, Identity> {
    constructor(http: HttpClient) { super(http); }
    get listUrl() { return '/api/identities'; }
}

export class IdentityDataSource extends SpringDataSource<HateoasEntityList<IdentityList,HateoasResourceLinks>, Identity> {
    constructor(identityService: IdentityService, errorHandler?: DataSourceErrorHandler) {
        super(identityService, errorHandler);
    }
    extractDataFromList(list:HateoasEntityList<IdentityList,HateoasResourceLinks>): Identity[] {
        return list && list._embedded && list._embedded.identities ? list._embedded.identities : null;
    }
}
