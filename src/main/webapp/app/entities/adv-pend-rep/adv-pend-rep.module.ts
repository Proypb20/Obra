import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdvPendRepComponent } from './list/adv-pend-rep.component';
import { AdvPendRepRoutingModule } from './route/adv-pend-rep-routing.module';

@NgModule({
  imports: [SharedModule, AdvPendRepRoutingModule],
  declarations: [AdvPendRepComponent],
})
export class AdvPendRepModule {}
