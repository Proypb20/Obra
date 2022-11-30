import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BalanceRepComponent } from '../list/balance-rep.component';
import { ASC } from 'app/config/navigation.constants';

const BalanceRepRoute: Routes = [
  {
    path: '',
    component: BalanceRepComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(BalanceRepRoute)],
  exports: [RouterModule],
})
export class BalanceRepRoutingModule {}
