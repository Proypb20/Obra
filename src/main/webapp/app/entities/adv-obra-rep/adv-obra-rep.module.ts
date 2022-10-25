import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdvObraRepComponent } from './list/adv-obra-rep.component';
import { AdvObraRepRoutingModule } from './route/adv-obra-rep-routing.module';

@NgModule({
  imports: [SharedModule, AdvObraRepRoutingModule],
  declarations: [AdvObraRepComponent],
})
export class AdvObraRepModule {}
