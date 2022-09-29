import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProveedorComponent } from '../list/proveedor.component';
import { ProveedorDetailComponent } from '../detail/proveedor-detail.component';
import { ProveedorUpdateComponent } from '../update/proveedor-update.component';
import { ProveedorRoutingResolveService } from './proveedor-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const proveedorRoute: Routes = [
  {
    path: '',
    component: ProveedorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProveedorDetailComponent,
    resolve: {
      proveedor: ProveedorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProveedorUpdateComponent,
    resolve: {
      proveedor: ProveedorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProveedorUpdateComponent,
    resolve: {
      proveedor: ProveedorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(proveedorRoute)],
  exports: [RouterModule],
})
export class ProveedorRoutingModule {}
