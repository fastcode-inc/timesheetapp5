import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { StatusDetailsComponent, StatusListComponent, StatusNewComponent } from './';

const routes: Routes = [
  // { path: '', component: StatusListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: StatusDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: StatusNewComponent, canActivate: [ AuthGuard ] },
];

export const statusRoute: ModuleWithProviders = RouterModule.forChild(routes);
