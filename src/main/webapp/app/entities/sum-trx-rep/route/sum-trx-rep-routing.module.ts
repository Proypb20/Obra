import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SumTrxRepComponent } from '../list/sum-trx-rep.component';
import { ASC } from 'app/config/navigation.constants';

const SumTrxRepRoute: Routes = [
  {
    path: '',
    component: SumTrxRepComponent,
    data: {
      defaultSort: 'obra,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(SumTrxRepRoute)],
  exports: [RouterModule],
})
export class SumTrxRepRoutingModule {}
