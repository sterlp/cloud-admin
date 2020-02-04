import { Component, OnInit } from '@angular/core';
import { Page, HateosEntityList, Pageable, HateosResourceLinks } from '@sterlp/ng-spring-boot-api';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IdentityList } from '../../api/identity-api.model';

@Component({
  selector: 'app-identities-list',
  templateUrl: './identities-list.page.html'
})
// tslint:disable-next-line: component-class-suffix
export class IdentitiesListPage implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit() {
    const q = new Pageable();
    q.size = 5;
    this.list(q).subscribe(r => {
      console.info(r);
      if (r.page.size > 0) {
        console.info(r._embedded.identities[0]);
      }
    });
  }

  list(pageable: Pageable): Observable<HateosEntityList<IdentityList, HateosResourceLinks>> {
    return this.http.get<HateosEntityList<IdentityList, HateosResourceLinks>>('/api/identities', {
      params: pageable ? pageable.newHttpParams() : null
    });
  }

}
