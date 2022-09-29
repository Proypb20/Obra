import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ListaPrecioComponent } from '../list/lista-precio.component';
import { ListaPrecioDetailComponent } from '../detail/lista-precio-detail.component';
import { ListaPrecioUpdateComponent } from '../update/lista-precio-update.component';
import { ListaPrecioRoutingResolveService } from './lista-precio-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const listaPrecioRoute: Routes = [
  {
    path: '',
    component: ListaPrecioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ListaPrecioDetailComponent,
    resolve: {
      listaPrecio: ListaPrecioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ListaPrecioUpdateComponent,
    resolve: {
      listaPrecio: ListaPrecioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ListaPrecioUpdateComponent,
    resolve: {
      listaPrecio: ListaPrecioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(listaPrecioRoute)],
  exports: [RouterModule],
})
export class ListaPrecioRoutingModule {}
