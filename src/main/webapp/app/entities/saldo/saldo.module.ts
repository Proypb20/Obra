import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SaldoComponent } from './list/saldo.component';
import { SaldoRoutingModule } from './route/saldo-routing.module';

@NgModule({
  imports: [SharedModule, SaldoRoutingModule],
  declarations: [SaldoComponent],
})
export class SaldoModule {}
