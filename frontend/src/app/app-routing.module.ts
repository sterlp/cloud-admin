import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomePage } from './home/pages/home.page';


const routes: Routes = [
  {path: 'home', component: HomePage},
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'identity', loadChildren: () => import('./identity/identities.module').then(m => m.IdentitiesModule) },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
