import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServerErrorComponent } from './spring/server-error/server-error.component';



@NgModule({
  declarations: [ServerErrorComponent],
  imports: [
    CommonModule
  ],
  exports: [ServerErrorComponent]
})
export class SharedModule { }
