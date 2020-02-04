import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IdentitiesListPage } from './identities-list.page';

describe('IdentitiesListPage', () => {
  let component: IdentitiesListPage;
  let fixture: ComponentFixture<IdentitiesListPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IdentitiesListPage ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IdentitiesListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
