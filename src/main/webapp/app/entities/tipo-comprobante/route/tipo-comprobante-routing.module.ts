import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TipoComprobanteComponent } from '../list/tipo-comprobante.component';
import { TipoComprobanteDetailComponent } from '../detail/tipo-comprobante-detail.component';
import { TipoComprobanteUpdateComponent } from '../update/tipo-comprobante-update.component';
import { TipoComprobanteRoutingResolveService } from './tipo-comprobante-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tipoComprobanteRoute: Routes = [
  {
    path: '',
    component: TipoComprobanteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoComprobanteDetailComponent,
    resolve: {
      tipoComprobante: TipoComprobanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoComprobanteUpdateComponent,
    resolve: {
      tipoComprobante: TipoComprobanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoComprobanteUpdateComponent,
    resolve: {
      tipoComprobante: TipoComprobanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tipoComprobanteRoute)],
  exports: [RouterModule],
})
export class TipoComprobanteRoutingModule {}
