import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ConceptoComponent } from '../list/concepto.component';
import { ConceptoDetailComponent } from '../detail/concepto-detail.component';
import { ConceptoUpdateComponent } from '../update/concepto-update.component';
import { ConceptoRoutingResolveService } from './concepto-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const conceptoRoute: Routes = [
  {
    path: '',
    component: ConceptoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptoDetailComponent,
    resolve: {
      concepto: ConceptoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptoUpdateComponent,
    resolve: {
      concepto: ConceptoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptoUpdateComponent,
    resolve: {
      concepto: ConceptoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(conceptoRoute)],
  exports: [RouterModule],
})
export class ConceptoRoutingModule {}
