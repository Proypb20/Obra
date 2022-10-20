import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SumTrxRepComponent } from './list/sum-trx-rep.component';
import { SumTrxRepRoutingModule } from './route/sum-trx-rep-routing.module';

@NgModule({
  imports: [SharedModule, SumTrxRepRoutingModule],
  declarations: [SumTrxRepComponent],
})
export class SumTrxRepModule {}
