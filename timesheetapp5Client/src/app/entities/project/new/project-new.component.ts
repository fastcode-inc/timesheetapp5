import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { ProjectService } from '../project.service';
import { IProject } from '../iproject';
import { Globals, BaseNewComponent, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { CustomerService } from 'src/app/entities/customer/customer.service';

@Component({
  selector: 'app-project-new',
  templateUrl: './project-new.component.html',
  styleUrls: ['./project-new.component.scss'],
})
export class ProjectNewComponent extends BaseNewComponent<IProject> implements OnInit {
  title: string = 'New Project';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<ProjectNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public projectService: ProjectService,
    public errorService: ErrorService,
    public customerService: CustomerService,
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
      projectService,
      errorService
    );
  }

  ngOnInit() {
    this.entityName = 'Project';
    this.setAssociations();
    super.ngOnInit();
    this.setForm();
    this.checkPassedData();
    this.setPickerSearchListener();
  }

  setForm() {
    this.itemForm = this.formBuilder.group({
      description: [''],
      enddate: [''],
      enddateTime: ['12:00 AM'],
      name: ['', Validators.required],
      startdate: [''],
      startdateTime: ['12:00 AM'],
      customerid: [''],
      customerDescriptiveField: [''],
    });

    this.fields = [
      {
        name: 'description',
        label: 'description',
        isRequired: false,
        isAutoGenerated: false,
        type: 'string',
      },
      {
        name: 'enddate',
        label: 'enddate',
        isRequired: false,
        isAutoGenerated: false,
        type: 'date',
      },
      {
        name: 'enddateTime',
        label: 'enddate Time',
        isRequired: false,
        isAutoGenerated: false,
        type: 'time',
      },
      {
        name: 'name',
        label: 'name',
        isRequired: true,
        isAutoGenerated: false,
        type: 'string',
      },
      {
        name: 'startdate',
        label: 'startdate',
        isRequired: false,
        isAutoGenerated: false,
        type: 'date',
      },
      {
        name: 'startdateTime',
        label: 'startdate Time',
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
            key: 'customerid',
            value: undefined,
            referencedkey: 'customerid',
          },
        ],
        isParent: false,
        table: 'customer',
        type: 'ManyToOne',
        service: this.customerService,
        label: 'customer',
        descriptiveField: 'customerDescriptiveField',
        referencedDescriptiveField: 'customerid',
      },
    ];
    this.parentAssociations = this.associations.filter((association) => {
      return !association.isParent;
    });
  }

  onSubmit() {
    let project = this.itemForm.getRawValue();
    project.enddate = this.dataService.combineDateAndTime(project.enddate, project.enddateTime);
    delete project.enddateTime;
    project.startdate = this.dataService.combineDateAndTime(project.startdate, project.startdateTime);
    delete project.startdateTime;
    super.onSubmit(project);
  }
}
