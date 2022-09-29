import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ObraComponent } from '../list/obra.component';
import { ObraDetailComponent } from '../detail/obra-detail.component';
import { ObraUpdateComponent } from '../update/obra-update.component';
import { ObraRoutingResolveService } from './obra-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const obraRoute: Routes = [
  {
    path: '',
    component: ObraComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ObraDetailComponent,
    resolve: {
      obra: ObraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ObraUpdateComponent,
    resolve: {
      obra: ObraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ObraUpdateComponent,
    resolve: {
      obra: ObraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(obraRoute)],
  exports: [RouterModule],
})
export class ObraRoutingModule {}
