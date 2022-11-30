import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BalanceRepComponent } from './list/balance-rep.component';
import { BalanceRepRoutingModule } from './route/balance-rep-routing.module';

@NgModule({
  imports: [SharedModule, BalanceRepRoutingModule],
  declarations: [BalanceRepComponent],
})
export class BalanceRepModule {}
