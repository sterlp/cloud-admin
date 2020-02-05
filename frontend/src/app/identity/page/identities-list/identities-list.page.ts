import { Component, OnInit } from '@angular/core';
import { IdentityList, Identity, IdentityModel } from '../../api/identity-api.model';
import { IdentityService, IdentityDataSource } from '../../service/identity.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-identities-list',
  templateUrl: './identities-list.page.html'
})
// tslint:disable-next-line: component-class-suffix
export class IdentitiesListPage implements OnInit {

  constructor(private identityService: IdentityService, private sanitizer: DomSanitizer) { }

  identityDataSource: IdentityDataSource;
  readonly identityColumns = IdentityModel.COLUMNS;
  readonly displayIdentityColumns: string[] = this.identityColumns.map(c => c.id);

  ngOnInit() {
    this.displayIdentityColumns.push('actions');
    this.identityDataSource = new IdentityDataSource(this.identityService);
    this.doLoad();
  }
  doLoad() {
    this.identityDataSource.load();
  }
}
