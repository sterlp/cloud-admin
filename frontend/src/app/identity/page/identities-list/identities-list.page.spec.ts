import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { IdentitiesListPage } from './identities-list.page';

describe('IdentitiesListPage', () => {
  let component: IdentitiesListPage;
  let fixture: ComponentFixture<IdentitiesListPage>;

  beforeEach(waitForAsync(() => {
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
