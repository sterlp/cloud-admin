import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { IdentityService } from '../../service/identity.service';
import { Identity, IdentityModel, AccountGenerationStrategy } from '../../api/identity-api.model';
import { Location } from '@angular/common';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import { SpringErrorWrapper } from 'src/app/shared/spring/api/spring-error.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-identity',
  templateUrl: './identity.page.html'
})
// tslint:disable: curly
// tslint:disable-next-line: component-class-suffix
export class IdentityPage implements OnInit, OnDestroy {

  constructor(private router: Router,
              private route: ActivatedRoute,
              private location: Location,
              private identityService: IdentityService) { }

  identity: Identity;
  readonly identityColumns = IdentityModel.COLUMNS;
  readonly serverError = new SpringErrorWrapper();

  private sub: Subscription | null = null;

  ngOnInit() {
    this.sub = this.route.params.subscribe({
      next: params => {
          const id = params.id * 1;
          if (isNaN(id)) {
            this._new();
          } else {
            this.doLoad(id);
          }
        }
      }
    );
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  _new() {
    this.identity = { id: null, name: null, accountStrategy: AccountGenerationStrategy.SAME_AS_IDENTITY_ID };
  }
  doLoad(id: number) {
    this.serverError.clear();
    this.identityService.get(id).subscribe(
      r => this.identity = r,
      e => {
        if (e.status === 404) this._new();
        else this.serverError.init(e.error || e);
      });
  }
  doSave(close: boolean) {
    this.serverError.clear();
    this.identityService.save(this.identity).subscribe(r => {
            this.identity = r;
            if (close) {
            this.location.back();
        }
    },
    e => this.serverError.init(e.error || e));
  }
}
