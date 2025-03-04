import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { SwaggerComponent } from 'src/app/swagger/swagger.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { AuthGuard } from './core/auth-guard';

const routes: Routes = [
  {
    path: '',
    loadChildren: './extended/core/core.module#CoreExtendedModule',
  },
  { path: 'swagger-ui', component: SwaggerComponent, canActivate: [AuthGuard] },
  {
    path: '',
    loadChildren: './extended/admin/admin.module#AdminExtendedModule',
  },
  {
    path: '',
    loadChildren: './extended/account/account.module#AccountExtendedModule',
  },
  {
    path: 'reporting',
    loadChildren: './reporting-module/reporting.module#ReportingModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'project',
    loadChildren: './extended/entities/project/project.module#ProjectExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'timesheetstatus',
    loadChildren: './extended/entities/timesheetstatus/timesheetstatus.module#TimesheetstatusExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'timesheet',
    loadChildren: './extended/entities/timesheet/timesheet.module#TimesheetExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'task',
    loadChildren: './extended/entities/task/task.module#TaskExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'timesheetdetails',
    loadChildren: './extended/entities/timesheetdetails/timesheetdetails.module#TimesheetdetailsExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'usertask',
    loadChildren: './extended/entities/usertask/usertask.module#UsertaskExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'status',
    loadChildren: './extended/entities/status/status.module#StatusExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'customer',
    loadChildren: './extended/entities/customer/customer.module#CustomerExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'scheduler',
    loadChildren: './scheduler/scheduler.module#SchedulerModule',
    canActivate: [AuthGuard],
  },
  { path: '**', component: ErrorPageComponent },
];

export const routingModule: ModuleWithProviders = RouterModule.forRoot(routes);
