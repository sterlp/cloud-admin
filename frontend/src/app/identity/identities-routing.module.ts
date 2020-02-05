import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { IdentitiesListPage } from './page/identities-list/identities-list.page';
import { IdentityPage } from './page/identity/identity.page'

const routes: Routes = [
  { path: '', component: IdentitiesListPage },
  { path: ':id', component: IdentityPage },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IdentitiesRoutingModule { }
