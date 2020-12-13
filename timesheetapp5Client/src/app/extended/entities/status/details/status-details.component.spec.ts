import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { StatusExtendedService, StatusDetailsExtendedComponent, StatusListExtendedComponent } from '../';
import { IStatus } from 'src/app/entities/status';
describe('StatusDetailsExtendedComponent', () => {
  let component: StatusDetailsExtendedComponent;
  let fixture: ComponentFixture<StatusDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [StatusDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [StatusExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StatusDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          StatusDetailsExtendedComponent,
          StatusListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'status', component: StatusDetailsExtendedComponent },
            { path: 'status/:id', component: StatusListExtendedComponent },
          ]),
        ],
        providers: [StatusExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StatusDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
