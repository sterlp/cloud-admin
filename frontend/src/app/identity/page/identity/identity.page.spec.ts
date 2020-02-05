import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IdentityPage } from './identity.page';

describe('IdentityPage', () => {
  let component: IdentityPage;
  let fixture: ComponentFixture<IdentityPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IdentityPage ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IdentityPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
