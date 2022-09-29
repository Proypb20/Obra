import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdvPendRepComponent } from '../list/adv-pend-rep.component';
import { ASC } from 'app/config/navigation.constants';

const advPendRepRoute: Routes = [
  {
    path: '',
    component: AdvPendRepComponent,
    data: {
      defaultSort: 'obra,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(advPendRepRoute)],
  exports: [RouterModule],
})
export class AdvPendRepRoutingModule {}
