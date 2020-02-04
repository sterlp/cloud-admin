import { Component, OnInit } from '@angular/core';
import { Page, HateosEntityList, Pageable, HateosResourceLinks, EMPTY_PAGE } from '@sterlp/ng-spring-boot-api';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IdentityList } from '../../api/identity-api.model';
import { IdentityService, IdentityDataSource } from '../../service/identity.service';

@Component({
  selector: 'app-identities-list',
  templateUrl: './identities-list.page.html'
})
// tslint:disable-next-line: component-class-suffix
export class IdentitiesListPage implements OnInit {

  constructor(private identityService: IdentityService) { }

  identityDataSource: IdentityDataSource;
  displayIdentityColumns: string[] = ['name', 'firstName'];

  ngOnInit() {
    this.identityDataSource = new IdentityDataSource(this.identityService);
    this.identityDataSource.load();
  }
}
