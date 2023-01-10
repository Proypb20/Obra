import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResObraRepComponent } from '../list/res-obra-rep.component';
import { ASC } from 'app/config/navigation.constants';

const ResObraRepRoute: Routes = [
  {
    path: '',
    component: ResObraRepComponent,
    data: {
      defaultSort: 'obraName,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ResObraRepRoute)],
  exports: [RouterModule],
})
export class ResObraRepRoutingModule {}
