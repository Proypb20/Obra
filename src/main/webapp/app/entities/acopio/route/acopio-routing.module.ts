import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AcopioComponent } from '../list/acopio.component';
import { AcopioDetailComponent } from '../detail/acopio-detail.component';
import { AcopioUpdateComponent } from '../update/acopio-update.component';
import { AcopioRoutingResolveService } from './acopio-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const acopioRoute: Routes = [
  {
    path: '',
    component: AcopioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AcopioDetailComponent,
    resolve: {
      acopio: AcopioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AcopioUpdateComponent,
    resolve: {
      acopio: AcopioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AcopioUpdateComponent,
    resolve: {
      acopio: AcopioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(acopioRoute)],
  exports: [RouterModule],
})
export class AcopioRoutingModule {}
