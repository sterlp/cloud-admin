import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Pageable, HateosEntityList, HateosResourceLinks } from '@sterlp/ng-spring-boot-api';
import { Observable, BehaviorSubject } from 'rxjs';
import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { IdentityList, Identity } from '../api/identity-api.model';

@Injectable({
  providedIn: 'root'
})
export class IdentityService {

  private listUrl = '/api/identities';

  constructor(private http: HttpClient) { }

  list(pageable: Pageable): Observable<HateosEntityList<IdentityList, HateosResourceLinks>> {
    return this.http.get<HateosEntityList<IdentityList, HateosResourceLinks>>(this.listUrl, {
      params: pageable ? pageable.newHttpParams() : null
    });
  }
  get(id: number | string): Observable<Identity> {
    return this.http.get<Identity>(`${this.listUrl}/${id}`);
  }
  save(entity: Identity): Observable<Identity> {
    if (entity.id) {
      return this.http.put<Identity>(`${this.listUrl}/${entity.id}`, entity);
    } else {
      return this.http.post<Identity>(`${this.listUrl}`, entity);
    }
  }
}

export class IdentityDataSource implements DataSource<Identity> {

  constructor(private identityService: IdentityService) {}

  private dataSubject = new BehaviorSubject<Identity[]>([]);
  private hateosSubject = new BehaviorSubject<HateosEntityList<IdentityList, HateosResourceLinks>>(null);

  public hateosSubject$ = this.hateosSubject.asObservable();

  connect(collectionViewer: CollectionViewer): Observable<Identity[] | readonly Identity[]> {
    return this.dataSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.dataSubject.complete();
    this.hateosSubject.complete();
  }

  load(page: number = 0, size: number = 10): void {
    console.info('loadConnectorData ...');
    this.identityService.list(Pageable.of(page, size))
      // .pipe(finalize(() => this.$loading.finishedLoading()))
      .subscribe(data => this.setData(data));
  }

  setData(data: HateosEntityList<IdentityList, HateosResourceLinks>) {
    this.hateosSubject.next(data);
    this.dataSubject.next(data._embedded ? data._embedded.identities : []);
  }
}
