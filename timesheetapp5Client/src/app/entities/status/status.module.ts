import { NgModule } from '@angular/core';

import { StatusDetailsComponent, StatusListComponent, StatusNewComponent } from './';
import { statusRoute } from './status.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [StatusDetailsComponent, StatusListComponent, StatusNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [statusRoute, SharedModule, GeneralComponentsModule],
})
export class StatusModule {}
