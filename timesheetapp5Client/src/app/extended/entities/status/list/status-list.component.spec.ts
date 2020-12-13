import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  StatusExtendedService,
  StatusDetailsExtendedComponent,
  StatusListExtendedComponent,
  StatusNewExtendedComponent,
} from '../';
import { IStatus } from 'src/app/entities/status';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('StatusListExtendedComponent', () => {
  let fixture: ComponentFixture<StatusListExtendedComponent>;
  let component: StatusListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [StatusListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [StatusExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StatusListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          StatusListExtendedComponent,
          StatusNewExtendedComponent,
          NewComponent,
          StatusDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'status', component: StatusListExtendedComponent },
            { path: 'status/:id', component: StatusDetailsExtendedComponent },
          ]),
        ],
        providers: [StatusExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StatusListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
