import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IdentitiesRoutingModule } from './identities-routing.module';
import { IdentitiesListPage } from './page/identities-list/identities-list.page';
import { IdentityPage } from './page/identity/identity.page';

import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule, MatSortHeader } from '@angular/material/sort';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatPaginatorModule } from '@angular/material/paginator';
import { SharedModule } from '../shared/shared.module';


// https://angular.io/guide/feature-modules
// https://angular.io/guide/lazy-loading-ngmodules
@NgModule({
  declarations: [IdentitiesListPage, IdentityPage],
  imports: [
    CommonModule,
    IdentitiesRoutingModule,
    FormsModule,
    MatSortModule,
    MatTableModule,
    MatIconModule,
    MatTooltipModule,
    MatInputModule,
    MatCardModule,
    MatButtonModule,
    MatPaginatorModule,
    MatSortModule,
    SharedModule
  ]
})
export class IdentitiesModule { }
