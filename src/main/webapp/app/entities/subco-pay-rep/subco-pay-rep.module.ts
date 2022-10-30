import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SubcoPayRepComponent } from './list/subco-pay-rep.component';
import { SubcoPayRepRoutingModule } from './route/subco-pay-rep-routing.module';

@NgModule({
  imports: [SharedModule, SubcoPayRepRoutingModule],
  declarations: [SubcoPayRepComponent],
})
export class SubcoPayRepModule {}
