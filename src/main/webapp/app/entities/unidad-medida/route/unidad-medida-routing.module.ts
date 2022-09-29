import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UnidadMedidaComponent } from '../list/unidad-medida.component';
import { UnidadMedidaDetailComponent } from '../detail/unidad-medida-detail.component';
import { UnidadMedidaUpdateComponent } from '../update/unidad-medida-update.component';
import { UnidadMedidaRoutingResolveService } from './unidad-medida-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const unidadMedidaRoute: Routes = [
  {
    path: '',
    component: UnidadMedidaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UnidadMedidaDetailComponent,
    resolve: {
      unidadMedida: UnidadMedidaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UnidadMedidaUpdateComponent,
    resolve: {
      unidadMedida: UnidadMedidaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UnidadMedidaUpdateComponent,
    resolve: {
      unidadMedida: UnidadMedidaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(unidadMedidaRoute)],
  exports: [RouterModule],
})
export class UnidadMedidaRoutingModule {}
