import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { TimesheetExtendedService } from '../timesheet.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { UsersExtendedService } from 'src/app/extended/admin/user-management/users/users.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { TimesheetDetailsComponent } from 'src/app/entities/timesheet/index';

@Component({
  selector: 'app-timesheet-details',
  templateUrl: './timesheet-details.component.html',
  styleUrls: ['./timesheet-details.component.scss'],
})
export class TimesheetDetailsExtendedComponent extends TimesheetDetailsComponent implements OnInit {
  title: string = 'Timesheet';
  parentUrl: string = 'timesheet';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public timesheetExtendedService: TimesheetExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public usersExtendedService: UsersExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      global,
      timesheetExtendedService,
      pickerDialogService,
      errorService,
      usersExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
