import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { IdentityList, Identity, IdentityModel } from '../../api/identity-api.model';
import { IdentityService, IdentityDataSource } from '../../service/identity.service';
import { DomSanitizer } from '@angular/platform-browser';
import { MatPaginator } from '@angular/material/paginator';
import { CompileShallowModuleMetadata } from '@angular/compiler';
import { MatSort } from '@angular/material/sort';
import { of } from 'rxjs';
import { SpringErrorWrapper } from 'src/app/shared/spring/api/spring-error.model';
import { PageRequest, Pageable } from '@sterlp/ng-spring-boot-api';

@Component({
  selector: 'app-identities-list',
  templateUrl: './identities-list.page.html'
})
// tslint:disable: component-class-suffix curly
export class IdentitiesListPage implements OnInit, AfterViewInit {
    @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
    @ViewChild(MatSort, {static: true}) sort: MatSort;

    constructor(private identityService: IdentityService, private sanitizer: DomSanitizer) { }

    identityDataSource: IdentityDataSource;
    filter: string;

    readonly serverError = new SpringErrorWrapper();
    readonly identityColumns = IdentityModel.COLUMNS;
    readonly displayIdentityColumns: string[] = this.identityColumns.map(c => c.id);

    ngOnInit() {
        this.displayIdentityColumns.push('actions');
        this.identityDataSource = new IdentityDataSource(this.identityService, (e) => {
            this.serverError.init(e);
            return of(this.identityDataSource.value);
        }, this._filterHook.bind(this));

        this.paginator.page.subscribe(this.identityDataSource.doPage.bind(this.identityDataSource));
        this.sort.sortChange.subscribe(this.identityDataSource.doSortBy.bind(this.identityDataSource));

        this.identityDataSource.hateosSubject$.subscribe(v => {
            if (v && v.page) this.paginator.length = v.page.totalElements;
        });
    }
    ngAfterViewInit(): void {
        this.identityDataSource.doLoad(this.paginator.pageIndex, this.paginator.pageSize);
    }
    doLoad() {
        this.identityDataSource.doLoad();
    }

    private _filterHook(identityService: IdentityService, page: Pageable) {
        if (this.filter) return identityService.search(this.filter, page);
        else return null;
    }
}
