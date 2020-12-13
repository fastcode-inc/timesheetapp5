import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { AuthGuard } from 'src/app/core/auth-guard';

// import { EntityHistoryComponent } from 'src/app/admin/entity-history/entity-history.component';

const routes: Routes = [
  {
    path: 'rolepermission',
    loadChildren: './user-management/rolepermission/rolepermission.module#RolepermissionModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'role',
    loadChildren: './user-management/role/role.module#RoleModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'permission',
    loadChildren: './user-management/permission/permission.module#PermissionModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'usersrole',
    loadChildren: './user-management/usersrole/usersrole.module#UsersroleModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'users',
    loadChildren: './user-management/users/users.module#UsersModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'userspermission',
    loadChildren: './user-management/userspermission/userspermission.module#UserspermissionModule',
    canActivate: [AuthGuard],
  },
  // { path: "entityHistory", component: EntityHistoryComponent,canActivate: [ AuthGuard ]},
];

export const routingModule: ModuleWithProviders = RouterModule.forChild(routes);
