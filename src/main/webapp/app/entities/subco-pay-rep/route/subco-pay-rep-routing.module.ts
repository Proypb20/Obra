import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SubcoPayRepComponent } from '../list/subco-pay-rep.component';
import { ASC } from 'app/config/navigation.constants';

const SubcoPayRepRoute: Routes = [
  {
    path: '',
    component: SubcoPayRepComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(SubcoPayRepRoute)],
  exports: [RouterModule],
})
export class SubcoPayRepRoutingModule {}
