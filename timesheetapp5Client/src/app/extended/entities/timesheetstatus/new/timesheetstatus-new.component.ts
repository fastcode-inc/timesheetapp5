import { Component, OnInit, Inject } from '@angular/core';
import { TimesheetstatusExtendedService } from '../timesheetstatus.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { StatusExtendedService } from 'src/app/extended/entities/status/status.service';
import { TimesheetExtendedService } from 'src/app/extended/entities/timesheet/timesheet.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { TimesheetstatusNewComponent } from 'src/app/entities/timesheetstatus/index';

@Component({
  selector: 'app-timesheetstatus-new',
  templateUrl: './timesheetstatus-new.component.html',
  styleUrls: ['./timesheetstatus-new.component.scss'],
})
export class TimesheetstatusNewExtendedComponent extends TimesheetstatusNewComponent implements OnInit {
  title: string = 'New Timesheetstatus';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<TimesheetstatusNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public timesheetstatusExtendedService: TimesheetstatusExtendedService,
    public errorService: ErrorService,
    public statusExtendedService: StatusExtendedService,
    public timesheetExtendedService: TimesheetExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      dialogRef,
      data,
      global,
      pickerDialogService,
      timesheetstatusExtendedService,
      errorService,
      statusExtendedService,
      timesheetExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
