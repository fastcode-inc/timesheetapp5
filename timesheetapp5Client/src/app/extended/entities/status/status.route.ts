import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { StatusDetailsExtendedComponent, StatusListExtendedComponent, StatusNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: StatusListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: StatusDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: StatusNewExtendedComponent, canActivate: [AuthGuard] },
];

export const statusRoute: ModuleWithProviders = RouterModule.forChild(routes);
