import {HateoasResourceLinks, HateosPage,HateoasEntityList } from '@sterlp/ng-spring-boot-api';

export enum SystemCredentialType {
    BASIC = 'BASIC', KEY = 'KEY'
}
export interface SystemCredential {
    id?: number;
    type: SystemCredentialType;
    name: string;
    user?: string;
    password?: string;
}

export interface SystemCredentialHateoasList extendsHateoasEntityList<SystemCredential,HateoasResourceLinks> {
    systemCredentials: SystemCredential[];
}
