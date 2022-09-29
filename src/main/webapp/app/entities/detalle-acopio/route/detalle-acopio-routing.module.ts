import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DetalleAcopioComponent } from '../list/detalle-acopio.component';
import { DetalleAcopioDetailComponent } from '../detail/detalle-acopio-detail.component';
import { DetalleAcopioUpdateComponent } from '../update/detalle-acopio-update.component';
import { DetalleAcopioRoutingResolveService } from './detalle-acopio-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const detalleAcopioRoute: Routes = [
  {
    path: '',
    component: DetalleAcopioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetalleAcopioDetailComponent,
    resolve: {
      detalleAcopio: DetalleAcopioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetalleAcopioUpdateComponent,
    resolve: {
      detalleAcopio: DetalleAcopioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetalleAcopioUpdateComponent,
    resolve: {
      detalleAcopio: DetalleAcopioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(detalleAcopioRoute)],
  exports: [RouterModule],
})
export class DetalleAcopioRoutingModule {}
