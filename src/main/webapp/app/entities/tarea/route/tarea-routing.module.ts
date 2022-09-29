import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TareaComponent } from '../list/tarea.component';
import { TareaDetailComponent } from '../detail/tarea-detail.component';
import { TareaUpdateComponent } from '../update/tarea-update.component';
import { TareaRoutingResolveService } from './tarea-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tareaRoute: Routes = [
  {
    path: '',
    component: TareaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TareaDetailComponent,
    resolve: {
      tarea: TareaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TareaUpdateComponent,
    resolve: {
      tarea: TareaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TareaUpdateComponent,
    resolve: {
      tarea: TareaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tareaRoute)],
  exports: [RouterModule],
})
export class TareaRoutingModule {}
