import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Pageable } from '@sterlp/ng-spring-boot-api';
import { Observable } from 'rxjs';

export abstract class SpringResoureceService<ListType, ResourceType>  {

    constructor(private http: HttpClient) { }

    /**
     * Return the URL which should be used to read a set of resources from the backend.
     */
    abstract get listUrl(): string;

    list(pageable: Pageable): Observable<ListType> {
        return this.http.get<ListType>(this.listUrl, {
            params: pageable ? pageable.newHttpParams() : null
        });
    }
    get(id: number | string): Observable<ResourceType> {
        return this.http.get<ResourceType>(`${this.listUrl}/${id}`);
    }
    save(entity: ResourceType, id?: number | string): Observable<ResourceType> {
        if (id) {
            return this.update(entity, id);
        } else {
            return this.create(entity);
        }
    }
    update(entity: ResourceType, id: number | string): Observable<ResourceType> {
        return this.http.put<ResourceType>(`${this.listUrl}/${id}`, entity);
    }
    create(entity: ResourceType): Observable<ResourceType> {
        return this.http.post<ResourceType>(`${this.listUrl}`, entity);
    }
}
