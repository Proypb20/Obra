import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdvObraRepComponent } from '../list/adv-obra-rep.component';
import { ASC } from 'app/config/navigation.constants';

const AdvObraRepRoute: Routes = [
  {
    path: '',
    component: AdvObraRepComponent,
    data: {
      defaultSort: 'obra,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(AdvObraRepRoute)],
  exports: [RouterModule],
})
export class AdvObraRepRoutingModule {}
