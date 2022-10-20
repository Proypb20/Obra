import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MovimientoComponent } from '../list/movimiento.component';
import { MovimientoDetailComponent } from '../detail/movimiento-detail.component';
import { MovimientoUpdateComponent } from '../update/movimiento-update.component';
import { MovimientoRoutingResolveService } from './movimiento-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const movimientoRoute: Routes = [
  {
    path: '',
    component: MovimientoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MovimientoDetailComponent,
    resolve: {
      movimiento: MovimientoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MovimientoUpdateComponent,
    resolve: {
      movimiento: MovimientoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MovimientoUpdateComponent,
    resolve: {
      movimiento: MovimientoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(movimientoRoute)],
  exports: [RouterModule],
})
export class MovimientoRoutingModule {}
