import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { UsertaskService } from '../usertask.service';
import { IUsertask } from '../iusertask';
import { BaseDetailsComponent, Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { TaskService } from 'src/app/entities/task/task.service';
import { UsersService } from 'src/app/admin/user-management/users/users.service';

@Component({
  selector: 'app-usertask-details',
  templateUrl: './usertask-details.component.html',
  styleUrls: ['./usertask-details.component.scss'],
})
export class UsertaskDetailsComponent extends BaseDetailsComponent<IUsertask> implements OnInit {
  title = 'Usertask';
  parentUrl = 'usertask';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public usertaskService: UsertaskService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public taskService: TaskService,
    public usersService: UsersService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(formBuilder, router, route, dialog, global, pickerDialogService, usertaskService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Usertask';
    this.setAssociations();
    super.ngOnInit();
    this.setForm();
    this.getItem();
    this.setPickerSearchListener();
  }

  setForm() {
    this.itemForm = this.formBuilder.group({
      id: ['', Validators.required],
      taskid: [''],
      taskDescriptiveField: [''],
      userid: [''],
      usersDescriptiveField: [''],
    });

    this.fields = [
      {
        name: 'id',
        label: 'id',
        isRequired: true,
        isAutoGenerated: false,
        type: 'number',
      },
    ];
  }

  onItemFetched(item: IUsertask) {
    this.item = item;
    this.itemForm.patchValue(item);
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
        label: 'task',
        service: this.taskService,
        descriptiveField: 'taskDescriptiveField',
        referencedDescriptiveField: 'id',
      },
      {
        column: [
          {
            key: 'userid',
            value: undefined,
            referencedkey: 'id',
          },
        ],
        isParent: false,
        table: 'users',
        type: 'ManyToOne',
        label: 'users',
        service: this.usersService,
        descriptiveField: 'usersDescriptiveField',
        referencedDescriptiveField: 'id',
      },
    ];

    this.childAssociations = this.associations.filter((association) => {
      return association.isParent;
    });

    this.parentAssociations = this.associations.filter((association) => {
      return !association.isParent;
    });
  }

  onSubmit() {
    let usertask = this.itemForm.getRawValue();
    super.onSubmit(usertask);
  }
}
