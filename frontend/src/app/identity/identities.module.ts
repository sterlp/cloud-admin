import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IdentitiesRoutingModule } from './identities-routing.module';
import { IdentitiesListPage } from './page/identities-list/identities-list.page';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

// https://angular.io/guide/feature-modules
// https://angular.io/guide/lazy-loading-ngmodules
@NgModule({
  declarations: [IdentitiesListPage],
  imports: [
    CommonModule,
    IdentitiesRoutingModule,
    FormsModule,
    HttpClientModule
  ]
})
export class IdentitiesModule { }
