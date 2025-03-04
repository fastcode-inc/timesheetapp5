import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { TimesheetdetailsExtendedService } from '../timesheetdetails.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { TaskExtendedService } from 'src/app/extended/entities/task/task.service';
import { TimesheetExtendedService } from 'src/app/extended/entities/timesheet/timesheet.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { TimesheetdetailsDetailsComponent } from 'src/app/entities/timesheetdetails/index';

@Component({
  selector: 'app-timesheetdetails-details',
  templateUrl: './timesheetdetails-details.component.html',
  styleUrls: ['./timesheetdetails-details.component.scss'],
})
export class TimesheetdetailsDetailsExtendedComponent extends TimesheetdetailsDetailsComponent implements OnInit {
  title: string = 'Timesheetdetails';
  parentUrl: string = 'timesheetdetails';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public timesheetdetailsExtendedService: TimesheetdetailsExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public taskExtendedService: TaskExtendedService,
    public timesheetExtendedService: TimesheetExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      global,
      timesheetdetailsExtendedService,
      pickerDialogService,
      errorService,
      taskExtendedService,
      timesheetExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
