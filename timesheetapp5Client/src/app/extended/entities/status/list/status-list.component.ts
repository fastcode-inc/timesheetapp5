import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { StatusExtendedService } from '../status.service';
import { StatusNewExtendedComponent } from '../new/status-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { StatusListComponent } from 'src/app/entities/status/index';

@Component({
  selector: 'app-status-list',
  templateUrl: './status-list.component.html',
  styleUrls: ['./status-list.component.scss'],
})
export class StatusListExtendedComponent extends StatusListComponent implements OnInit {
  title: string = 'Status';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public statusService: StatusExtendedService,
    public errorService: ErrorService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      statusService,
      errorService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(StatusNewExtendedComponent);
  }
}
