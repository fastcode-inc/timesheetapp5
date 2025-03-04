import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { TimesheetstatusService } from '../timesheetstatus.service';
import { ITimesheetstatus } from '../itimesheetstatus';
import { Globals, BaseNewComponent, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { StatusService } from 'src/app/entities/status/status.service';
import { TimesheetService } from 'src/app/entities/timesheet/timesheet.service';

@Component({
  selector: 'app-timesheetstatus-new',
  templateUrl: './timesheetstatus-new.component.html',
  styleUrls: ['./timesheetstatus-new.component.scss'],
})
export class TimesheetstatusNewComponent extends BaseNewComponent<ITimesheetstatus> implements OnInit {
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
    public timesheetstatusService: TimesheetstatusService,
    public errorService: ErrorService,
    public statusService: StatusService,
    public timesheetService: TimesheetService,
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
      timesheetstatusService,
      errorService
    );
  }

  ngOnInit() {
    this.entityName = 'Timesheetstatus';
    this.setAssociations();
    super.ngOnInit();
    this.setForm();
    this.checkPassedData();
    this.setPickerSearchListener();
  }

  setForm() {
    this.itemForm = this.formBuilder.group({
      notes: [''],
      statuschangedate: [''],
      statuschangedateTime: ['12:00 AM'],
      statusid: ['', Validators.required],
      timesheetid: ['', Validators.required],
      statusDescriptiveField: [''],
      timesheetDescriptiveField: [''],
    });

    this.fields = [
      {
        name: 'notes',
        label: 'notes',
        isRequired: false,
        isAutoGenerated: false,
        type: 'string',
      },
      {
        name: 'statuschangedate',
        label: 'statuschangedate',
        isRequired: false,
        isAutoGenerated: false,
        type: 'date',
      },
      {
        name: 'statuschangedateTime',
        label: 'statuschangedate Time',
        isRequired: false,
        isAutoGenerated: false,
        type: 'time',
      },
    ];
  }

  setAssociations() {
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
        table: 'status',
        type: 'ManyToOne',
        service: this.statusService,
        label: 'status',
        descriptiveField: 'statusDescriptiveField',
        referencedDescriptiveField: 'id',
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
        table: 'timesheet',
        type: 'ManyToOne',
        service: this.timesheetService,
        label: 'timesheet',
        descriptiveField: 'timesheetDescriptiveField',
        referencedDescriptiveField: 'id',
      },
    ];
    this.parentAssociations = this.associations.filter((association) => {
      return !association.isParent;
    });
  }

  onSubmit() {
    let timesheetstatus = this.itemForm.getRawValue();
    timesheetstatus.statuschangedate = this.dataService.combineDateAndTime(
      timesheetstatus.statuschangedate,
      timesheetstatus.statuschangedateTime
    );
    delete timesheetstatus.statuschangedateTime;
    super.onSubmit(timesheetstatus);
  }
}
