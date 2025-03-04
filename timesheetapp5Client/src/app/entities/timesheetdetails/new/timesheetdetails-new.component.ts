import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { TimesheetdetailsService } from '../timesheetdetails.service';
import { ITimesheetdetails } from '../itimesheetdetails';
import { Globals, BaseNewComponent, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { TaskService } from 'src/app/entities/task/task.service';
import { TimesheetService } from 'src/app/entities/timesheet/timesheet.service';

@Component({
  selector: 'app-timesheetdetails-new',
  templateUrl: './timesheetdetails-new.component.html',
  styleUrls: ['./timesheetdetails-new.component.scss'],
})
export class TimesheetdetailsNewComponent extends BaseNewComponent<ITimesheetdetails> implements OnInit {
  title: string = 'New Timesheetdetails';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<TimesheetdetailsNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public timesheetdetailsService: TimesheetdetailsService,
    public errorService: ErrorService,
    public taskService: TaskService,
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
      timesheetdetailsService,
      errorService
    );
  }

  ngOnInit() {
    this.entityName = 'Timesheetdetails';
    this.setAssociations();
    super.ngOnInit();
    this.setForm();
    this.checkPassedData();
    this.setPickerSearchListener();
  }

  setForm() {
    this.itemForm = this.formBuilder.group({
      hours: [''],
      workdate: [''],
      workdateTime: ['12:00 AM'],
      taskid: [''],
      taskDescriptiveField: [''],
      timesheetid: [''],
      timesheetDescriptiveField: [''],
    });

    this.fields = [
      {
        name: 'hours',
        label: 'hours',
        isRequired: false,
        isAutoGenerated: false,
        type: 'number',
      },
      {
        name: 'workdate',
        label: 'workdate',
        isRequired: false,
        isAutoGenerated: false,
        type: 'date',
      },
      {
        name: 'workdateTime',
        label: 'workdate Time',
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
            key: 'taskid',
            value: undefined,
            referencedkey: 'id',
          },
        ],
        isParent: false,
        table: 'task',
        type: 'ManyToOne',
        service: this.taskService,
        label: 'task',
        descriptiveField: 'taskDescriptiveField',
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
    let timesheetdetails = this.itemForm.getRawValue();
    timesheetdetails.workdate = this.dataService.combineDateAndTime(
      timesheetdetails.workdate,
      timesheetdetails.workdateTime
    );
    delete timesheetdetails.workdateTime;
    super.onSubmit(timesheetdetails);
  }
}
