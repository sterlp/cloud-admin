import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SystemsListComponent } from './page/systems-list/systems-list.component';
import { SystemComponent } from './service/system/system.component';



@NgModule({
  declarations: [SystemsListComponent, SystemComponent],
  imports: [
    CommonModule
  ]
})
export class SystemModule { }
