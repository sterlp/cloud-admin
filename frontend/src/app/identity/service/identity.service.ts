import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Pageable, HateosEntityList, HateosResourceLinks, Sort } from '@sterlp/ng-spring-boot-api';
import { Observable, BehaviorSubject, of, Subscription } from 'rxjs';
import { catchError, map, tap, finalize } from 'rxjs/operators';
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
/**
 * Default page request interface, like from Anular Material Paginator
 * https://material.angular.io/components/paginator/overview
 */
export interface PageRequest {
    /** the page to show, starting with 0 */
    pageIndex: number;
    /** the number of elements to show */
    pageSize: number;
}

export type ErrorHandler = (operation: string, error: any) => Observable<any>;

export class IdentityDataSource implements DataSource<Identity> {

    constructor(private identityService: IdentityService, private errorHandler?: ErrorHandler) {}

    // tslint:disable: variable-name
    private _lastRequest: Subscription;
    // tslint:disable-next-line: variable-name
    private _loading = new BehaviorSubject<boolean>(false);
    public readonly loading$ = this._loading.asObservable();

    private _page = new Pageable();
    get page(): Pageable {
        return this._page;
    }
    private dataSubject = new BehaviorSubject<Identity[]>([]);
    private hateosSubject = new BehaviorSubject<HateosEntityList<IdentityList, HateosResourceLinks>>(null);
    public hateosSubject$ = this.hateosSubject.asObservable();

    connect(collectionViewer: CollectionViewer): Observable<Identity[] | readonly Identity[]> {
        return this.dataSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.dataSubject.complete();
        this.hateosSubject.complete();
        this._loading.complete();
    }

    doLoad(page: number = Pageable.DEFAULT_PAGE, size: number = Pageable.DEFAULT_SIZE): void {
        this._page.size = size;
        this._page.page = page;
        this._loadData();
    }

    doPage(page: PageRequest | Pageable | any): void {
        if (page) {
            if (page instanceof  Pageable) {
                console.info('Pageable load ...', this._page);
                this._page = page;
            } else {
                this._page.page = page.pageIndex || page.index || page.page || Pageable.DEFAULT_PAGE;
                this._page.size = page.pageSize || page.size || Pageable.DEFAULT_SIZE;
                console.info('compatibility load ...', this._page);
            }
            this._loadData();
        } else {
            throw new TypeError('Given page is null.');
        }
    }

    doSortBy(sort: Sort | any) {
        if (sort) {
            this._page.setSort(sort.field || sort.active, sort.direction);
            this._loadData();
        }
    }

    setData(data: HateosEntityList<IdentityList, HateosResourceLinks>) {
        this.hateosSubject.next(data);
        this.dataSubject.next(data._embedded ? data._embedded.identities : []);
    }

    /**
     * Requests the data using the actual page.
     */
    private _loadData() {
        if (this._lastRequest) { // cancel any pending requests ...
            this._lastRequest.unsubscribe();
        }
        this._loading.next(true);
        this._lastRequest = this.identityService.list(this._page)
            .subscribe(
                result => this.setData(result),
                e => this.errorHandler ? this.errorHandler('loadData', e) : null,
                () => {
                    this._loading.next(false);
                    this._lastRequest.unsubscribe();
                    this._lastRequest = null;
                }
            );
    }
}
