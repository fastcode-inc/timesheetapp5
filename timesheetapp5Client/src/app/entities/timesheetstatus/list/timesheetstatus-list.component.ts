import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { ITimesheetstatus } from '../itimesheetstatus';
import { TimesheetstatusService } from '../timesheetstatus.service';
import { Router, ActivatedRoute } from '@angular/router';
import { TimesheetstatusNewComponent } from '../new/timesheetstatus-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { StatusService } from 'src/app/entities/status/status.service';
import { TimesheetService } from 'src/app/entities/timesheet/timesheet.service';

@Component({
  selector: 'app-timesheetstatus-list',
  templateUrl: './timesheetstatus-list.component.html',
  styleUrls: ['./timesheetstatus-list.component.scss'],
})
export class TimesheetstatusListComponent extends BaseListComponent<ITimesheetstatus> implements OnInit {
  title = 'Timesheetstatus';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public timesheetstatusService: TimesheetstatusService,
    public errorService: ErrorService,
    public statusService: StatusService,
    public timesheetService: TimesheetService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, timesheetstatusService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Timesheetstatus';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['statusid', 'timesheetid'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'statusid',
            value: undefined,
            referencedkey: 'id',
          },
        ],
        isParent: false,
        descriptiveField: 'statusDescriptiveField',
        referencedDescriptiveField: 'id',
        service: this.statusService,
        associatedObj: undefined,
        table: 'status',
        type: 'ManyToOne',
        url: 'timesheetstatus',
        listColumn: 'status',
        label: 'status',
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
        url: 'timesheetstatus',
        listColumn: 'timesheet',
        label: 'timesheet',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'notes',
        searchColumn: 'notes',
        label: 'notes',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'statuschangedate',
        searchColumn: 'statuschangedate',
        label: 'statuschangedate',
        sort: true,
        filter: true,
        type: listColumnType.DateTime,
      },
      {
        column: 'statusDescriptiveField',
        searchColumn: 'status',
        label: 'status',
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
      comp = TimesheetstatusNewComponent;
    }
    super.addNew(comp);
  }
}
