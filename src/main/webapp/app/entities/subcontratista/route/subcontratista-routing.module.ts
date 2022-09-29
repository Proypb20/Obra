import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SubcontratistaComponent } from '../list/subcontratista.component';
import { SubcontratistaDetailComponent } from '../detail/subcontratista-detail.component';
import { SubcontratistaUpdateComponent } from '../update/subcontratista-update.component';
import { SubcontratistaRoutingResolveService } from './subcontratista-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const subcontratistaRoute: Routes = [
  {
    path: '',
    component: SubcontratistaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubcontratistaDetailComponent,
    resolve: {
      subcontratista: SubcontratistaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubcontratistaUpdateComponent,
    resolve: {
      subcontratista: SubcontratistaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubcontratistaUpdateComponent,
    resolve: {
      subcontratista: SubcontratistaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(subcontratistaRoute)],
  exports: [RouterModule],
})
export class SubcontratistaRoutingModule {}
