import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { PermissionService } from '../permission.service';
import { IPermission } from '../ipermission';
import { BaseDetailsComponent, Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

@Component({
  selector: 'app-permission-details',
  templateUrl: './permission-details.component.html',
  styleUrls: ['./permission-details.component.scss'],
})
export class PermissionDetailsComponent extends BaseDetailsComponent<IPermission> implements OnInit {
  title = 'Permission';
  parentUrl = 'permission';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public permissionService: PermissionService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(formBuilder, router, route, dialog, global, pickerDialogService, permissionService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Permission';
    this.setAssociations();
    super.ngOnInit();
    this.setForm();
    this.getItem();
    this.setPickerSearchListener();
  }

  setForm() {
    this.itemForm = this.formBuilder.group({
      displayName: ['', Validators.required],
      id: [{ value: '', disabled: true }, Validators.required],
      name: ['', Validators.required],
    });

    this.fields = [
      {
        name: 'displayName',
        label: 'display Name',
        isRequired: true,
        isAutoGenerated: false,
        type: 'string',
      },
      {
        name: 'id',
        label: 'id',
        isRequired: true,
        isAutoGenerated: true,
        type: 'number',
      },
      {
        name: 'name',
        label: 'name',
        isRequired: true,
        isAutoGenerated: false,
        type: 'string',
      },
    ];
  }

  onItemFetched(item: IPermission) {
    this.item = item;
    this.itemForm.patchValue(item);
  }

  setAssociations() {
    this.associations = [
      {
        column: [],
        isParent: true,
        table: 'rolepermission',
        type: 'OneToMany',
        label: 'rolepermissions',
      },
      {
        column: [],
        isParent: true,
        table: 'userspermission',
        type: 'OneToMany',
        label: 'userspermissions',
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
    let permission = this.itemForm.getRawValue();
    super.onSubmit(permission);
  }
}
