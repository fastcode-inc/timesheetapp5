import { NgModule } from '@angular/core';

// import { EntityHistoryComponent } from 'src/app/admin/entity-history/entity-history.component';

import { routingModule } from './admin.routing';
import { SharedModule } from 'src/app/common/shared';

const entities = [
  // EntityHistoryComponent,
];

@NgModule({
  declarations: entities,
  exports: entities,
  imports: [routingModule, SharedModule],
})
export class AdminModule {}
