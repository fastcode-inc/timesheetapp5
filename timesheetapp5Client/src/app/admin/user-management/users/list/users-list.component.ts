import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { IUsers } from '../iusers';
import { UsersService } from '../users.service';
import { Router, ActivatedRoute } from '@angular/router';
import { UsersNewComponent } from '../new/users-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss'],
})
export class UsersListComponent extends BaseListComponent<IUsers> implements OnInit {
  title = 'Users';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public usersService: UsersService,
    public errorService: ErrorService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, usersService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Users';
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
            key: 'usersId',
            value: undefined,
            referencedkey: 'id',
          },
        ],
        isParent: true,
        table: 'userspermission',
        type: 'OneToMany',
      },
      {
        column: [
          {
            key: 'usersId',
            value: undefined,
            referencedkey: 'id',
          },
        ],
        isParent: true,
        table: 'usersrole',
        type: 'OneToMany',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'emailaddress',
        searchColumn: 'emailaddress',
        label: 'emailaddress',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'firstname',
        searchColumn: 'firstname',
        label: 'firstname',
        sort: true,
        filter: true,
        type: listColumnType.String,
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
        column: 'isactive',
        searchColumn: 'isactive',
        label: 'isactive',
        sort: true,
        filter: true,
        type: listColumnType.Boolean,
      },
      {
        column: 'isemailconfirmed',
        searchColumn: 'isemailconfirmed',
        label: 'isemailconfirmed',
        sort: true,
        filter: true,
        type: listColumnType.Boolean,
      },
      {
        column: 'lastname',
        searchColumn: 'lastname',
        label: 'lastname',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'username',
        searchColumn: 'username',
        label: 'username',
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
      comp = UsersNewComponent;
    }
    super.addNew(comp);
  }
}
