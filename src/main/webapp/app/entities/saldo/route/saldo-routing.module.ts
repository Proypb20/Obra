import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SaldoComponent } from '../list/saldo.component';
import { ASC } from 'app/config/navigation.constants';

const SaldoRoute: Routes = [
  {
    path: '',
    component: SaldoComponent,
    data: {
      defaultSort: 'metodoPago,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(SaldoRoute)],
  exports: [RouterModule],
})
export class SaldoRoutingModule {}
