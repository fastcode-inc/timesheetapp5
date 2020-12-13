import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { TimesheetdetailsExtendedService } from '../timesheetdetails.service';
import { TimesheetdetailsNewExtendedComponent } from '../new/timesheetdetails-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { TaskExtendedService } from 'src/app/extended/entities/task/task.service';
import { TimesheetExtendedService } from 'src/app/extended/entities/timesheet/timesheet.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { TimesheetdetailsListComponent } from 'src/app/entities/timesheetdetails/index';

@Component({
  selector: 'app-timesheetdetails-list',
  templateUrl: './timesheetdetails-list.component.html',
  styleUrls: ['./timesheetdetails-list.component.scss'],
})
export class TimesheetdetailsListExtendedComponent extends TimesheetdetailsListComponent implements OnInit {
  title: string = 'Timesheetdetails';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public timesheetdetailsService: TimesheetdetailsExtendedService,
    public errorService: ErrorService,
    public taskService: TaskExtendedService,
    public timesheetService: TimesheetExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      timesheetdetailsService,
      errorService,
      taskService,
      timesheetService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(TimesheetdetailsNewExtendedComponent);
  }
}
