import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { IdentityList, Identity } from '../api/identity-api.model';
import { SpringResource,
    HateoasEntityList,
    HateoasResourceLinks,
    DataSourceErrorHandler,
    SpringDataSource,
    Pageable} from '@sterlp/ng-spring-boot-api';

@Injectable({
  providedIn: 'root'
})
export class IdentityService extends SpringResource<HateoasEntityList<IdentityList, HateoasResourceLinks>, Identity> {
    constructor(http: HttpClient) {
        super(http);
    }
    get listUrl() { return '/api/identities'; }

    search(search: string, page: Pageable) {
        let params = page.newHttpParams();
        params = params.set('value', search);
        return this.http.get<HateoasEntityList<IdentityList, HateoasResourceLinks>>(
            this.listUrl + '/search/all', { params }
        );
    }
}

export class IdentityDataSource extends SpringDataSource<HateoasEntityList<IdentityList, HateoasResourceLinks>, Identity, IdentityService> {
    extractDataFromList(list: HateoasEntityList<IdentityList, HateoasResourceLinks>): Identity[] {
        return list && list._embedded && list._embedded.identities ? list._embedded.identities : null;
    }
}
