import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { IdentityService } from '../../service/identity.service';
import { Identity, IdentityModel, AccountGenerationStrategy } from '../../api/identity-api.model';
import { Location } from '@angular/common';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import { SpringErrorWrapper } from 'src/app/shared/spring/spring-error.model';

@Component({
  selector: 'app-identity',
  templateUrl: './identity.page.html'
})
// tslint:disable-next-line: component-class-suffix
export class IdentityPage implements OnInit {

  constructor(private router: Router,
              private route: ActivatedRoute,
              private location: Location,
              private identityService: IdentityService) { }

  identity: Identity;
  readonly identityColumns = IdentityModel.COLUMNS;
  readonly serverError = new SpringErrorWrapper();

  identityForm = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  ngOnInit() {
    this.route.params.subscribe({
      next: params => {
          if (!params.id || 'new' === params.id) {
            this.identity = { id: null, name: null, accountStrategy: AccountGenerationStrategy.SAME_AS_IDENTITY_ID };
          } else {
            this.doLoad(params.id * 1);
          }
        }
      }
    );
  }

    doLoad(id: number) {
        this.serverError.clear();
        this.identityService.get(id).subscribe(r => this.identity = r);
    }
    doSave(close: boolean) {
        this.serverError.clear();
        this.identityService.save(this.identity).subscribe(r => {
                this.identity = r;
                if (close) {
                this.location.back();
            }
        },
        e => {
            this.serverError.init(e.error || e);
        });
    }
}
