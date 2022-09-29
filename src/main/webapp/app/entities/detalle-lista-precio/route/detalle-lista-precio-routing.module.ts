import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DetalleListaPrecioComponent } from '../list/detalle-lista-precio.component';
import { DetalleListaPrecioDetailComponent } from '../detail/detalle-lista-precio-detail.component';
import { DetalleListaPrecioUpdateComponent } from '../update/detalle-lista-precio-update.component';
import { DetalleListaPrecioRoutingResolveService } from './detalle-lista-precio-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const detalleListaPrecioRoute: Routes = [
  {
    path: '',
    component: DetalleListaPrecioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetalleListaPrecioDetailComponent,
    resolve: {
      detalleListaPrecio: DetalleListaPrecioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetalleListaPrecioUpdateComponent,
    resolve: {
      detalleListaPrecio: DetalleListaPrecioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetalleListaPrecioUpdateComponent,
    resolve: {
      detalleListaPrecio: DetalleListaPrecioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(detalleListaPrecioRoute)],
  exports: [RouterModule],
})
export class DetalleListaPrecioRoutingModule {}
