import { Component, OnInit, Inject } from '@angular/core';
import { StatusExtendedService } from '../status.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { StatusNewComponent } from 'src/app/entities/status/index';

@Component({
  selector: 'app-status-new',
  templateUrl: './status-new.component.html',
  styleUrls: ['./status-new.component.scss'],
})
export class StatusNewExtendedComponent extends StatusNewComponent implements OnInit {
  title: string = 'New Status';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<StatusNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public statusExtendedService: StatusExtendedService,
    public errorService: ErrorService,
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
      statusExtendedService,
      errorService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
