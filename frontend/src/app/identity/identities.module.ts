import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IdentitiesRoutingModule } from './identities-routing.module';
import { IdentitiesListPage } from './page/identities-list/identities-list.page';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { IdentityService } from './service/identity.service';


// https://angular.io/guide/feature-modules
// https://angular.io/guide/lazy-loading-ngmodules
@NgModule({
  declarations: [IdentitiesListPage],
  imports: [
    CommonModule,
    IdentitiesRoutingModule,
    FormsModule,
    MatSortModule,
    MatTableModule
  ]
})
export class IdentitiesModule { }
