import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { TimesheetExtendedService } from '../timesheet.service';
import { TimesheetNewExtendedComponent } from '../new/timesheet-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { UsersExtendedService } from 'src/app/extended/admin/user-management/users/users.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { TimesheetListComponent } from 'src/app/entities/timesheet/index';

@Component({
  selector: 'app-timesheet-list',
  templateUrl: './timesheet-list.component.html',
  styleUrls: ['./timesheet-list.component.scss'],
})
export class TimesheetListExtendedComponent extends TimesheetListComponent implements OnInit {
  title: string = 'Timesheet';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public timesheetService: TimesheetExtendedService,
    public errorService: ErrorService,
    public usersService: UsersExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      timesheetService,
      errorService,
      usersService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(TimesheetNewExtendedComponent);
  }
}
