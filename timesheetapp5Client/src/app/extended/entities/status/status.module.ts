import { NgModule } from '@angular/core';

import {
  StatusExtendedService,
  StatusDetailsExtendedComponent,
  StatusListExtendedComponent,
  StatusNewExtendedComponent,
} from './';
import { StatusService } from 'src/app/entities/status';
import { StatusModule } from 'src/app/entities/status/status.module';
import { statusRoute } from './status.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [StatusDetailsExtendedComponent, StatusListExtendedComponent, StatusNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [statusRoute, StatusModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: StatusService, useClass: StatusExtendedService }],
})
export class StatusExtendedModule {}
