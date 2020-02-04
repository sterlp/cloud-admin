import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { IdentitiesListPage } from './page/identities-list/identities-list.page';

const routes: Routes = [{ path: '', component: IdentitiesListPage }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IdentitiesRoutingModule { }
