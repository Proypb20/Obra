import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProvinciaComponent } from '../list/provincia.component';
import { ProvinciaDetailComponent } from '../detail/provincia-detail.component';
import { ProvinciaUpdateComponent } from '../update/provincia-update.component';
import { ProvinciaRoutingResolveService } from './provincia-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const provinciaRoute: Routes = [
  {
    path: '',
    component: ProvinciaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProvinciaDetailComponent,
    resolve: {
      provincia: ProvinciaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProvinciaUpdateComponent,
    resolve: {
      provincia: ProvinciaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProvinciaUpdateComponent,
    resolve: {
      provincia: ProvinciaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(provinciaRoute)],
  exports: [RouterModule],
})
export class ProvinciaRoutingModule {}
