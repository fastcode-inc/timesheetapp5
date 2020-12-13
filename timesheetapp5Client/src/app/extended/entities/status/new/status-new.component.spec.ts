import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { StatusExtendedService, StatusNewExtendedComponent } from '../';
import { IStatus } from 'src/app/entities/status';
import { NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('StatusNewExtendedComponent', () => {
  let component: StatusNewExtendedComponent;
  let fixture: ComponentFixture<StatusNewExtendedComponent>;

  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [StatusNewExtendedComponent, NewComponent, FieldsComp],
        imports: [TestingModule],
        providers: [StatusExtendedService, { provide: MAT_DIALOG_DATA, useValue: {} }],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StatusNewExtendedComponent);
      component = fixture.componentInstance;
      spyOn(component, 'manageScreenResizing').and.returnValue();
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    describe('', () => {
      beforeEach(async(() => {
        TestBed.configureTestingModule({
          declarations: [StatusNewExtendedComponent, NewComponent, FieldsComp].concat(EntryComponents),
          imports: [TestingModule],
          providers: [StatusExtendedService, { provide: MAT_DIALOG_DATA, useValue: {} }],
        });
      }));

      beforeEach(() => {
        fixture = TestBed.createComponent(StatusNewExtendedComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });
    });
  });
});
