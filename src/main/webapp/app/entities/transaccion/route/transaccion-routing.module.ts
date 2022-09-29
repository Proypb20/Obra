import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TransaccionComponent } from '../list/transaccion.component';
import { TransaccionDetailComponent } from '../detail/transaccion-detail.component';
import { TransaccionUpdateComponent } from '../update/transaccion-update.component';
import { TransaccionRoutingResolveService } from './transaccion-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const transaccionRoute: Routes = [
  {
    path: '',
    component: TransaccionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransaccionDetailComponent,
    resolve: {
      transaccion: TransaccionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransaccionUpdateComponent,
    resolve: {
      transaccion: TransaccionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransaccionUpdateComponent,
    resolve: {
      transaccion: TransaccionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transaccionRoute)],
  exports: [RouterModule],
})
export class TransaccionRoutingModule {}
