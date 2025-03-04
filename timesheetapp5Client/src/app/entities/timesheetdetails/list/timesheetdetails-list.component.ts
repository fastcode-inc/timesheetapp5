import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { ITimesheetdetails } from '../itimesheetdetails';
import { TimesheetdetailsService } from '../timesheetdetails.service';
import { Router, ActivatedRoute } from '@angular/router';
import { TimesheetdetailsNewComponent } from '../new/timesheetdetails-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { TaskService } from 'src/app/entities/task/task.service';
import { TimesheetService } from 'src/app/entities/timesheet/timesheet.service';

@Component({
  selector: 'app-timesheetdetails-list',
  templateUrl: './timesheetdetails-list.component.html',
  styleUrls: ['./timesheetdetails-list.component.scss'],
})
export class TimesheetdetailsListComponent extends BaseListComponent<ITimesheetdetails> implements OnInit {
  title = 'Timesheetdetails';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public timesheetdetailsService: TimesheetdetailsService,
    public errorService: ErrorService,
    public taskService: TaskService,
    public timesheetService: TimesheetService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      dialog,
      global,
      changeDetectorRefs,
      pickerDialogService,
      timesheetdetailsService,
      errorService
    );
  }

  ngOnInit() {
    this.entityName = 'Timesheetdetails';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['id'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'taskid',
            value: undefined,
            referencedkey: 'id',
          },
        ],
        isParent: false,
        descriptiveField: 'taskDescriptiveField',
        referencedDescriptiveField: 'id',
        service: this.taskService,
        associatedObj: undefined,
        table: 'task',
        type: 'ManyToOne',
        url: 'timesheetdetails',
        listColumn: 'task',
        label: 'task',
      },
      {
        column: [
          {
            key: 'timesheetid',
            value: undefined,
            referencedkey: 'id',
          },
        ],
        isParent: false,
        descriptiveField: 'timesheetDescriptiveField',
        referencedDescriptiveField: 'id',
        service: this.timesheetService,
        associatedObj: undefined,
        table: 'timesheet',
        type: 'ManyToOne',
        url: 'timesheetdetails',
        listColumn: 'timesheet',
        label: 'timesheet',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'hours',
        searchColumn: 'hours',
        label: 'hours',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'id',
        searchColumn: 'id',
        label: 'id',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'workdate',
        searchColumn: 'workdate',
        label: 'workdate',
        sort: true,
        filter: true,
        type: listColumnType.DateTime,
      },
      {
        column: 'taskDescriptiveField',
        searchColumn: 'task',
        label: 'task',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'timesheetDescriptiveField',
        searchColumn: 'timesheet',
        label: 'timesheet',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'actions',
        label: 'Actions',
        sort: false,
        filter: false,
        type: listColumnType.String,
      },
    ];
    this.selectedColumns = this.columns;
    this.displayedColumns = this.columns.map((obj) => {
      return obj.column;
    });
  }
  addNew(comp) {
    if (!comp) {
      comp = TimesheetdetailsNewComponent;
    }
    super.addNew(comp);
  }
}
