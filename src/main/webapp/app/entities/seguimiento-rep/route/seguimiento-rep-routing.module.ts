import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SeguimientoRepComponent } from '../list/seguimiento-rep.component';
import { ASC } from 'app/config/navigation.constants';

const SeguimientoRepRoute: Routes = [
  {
    path: '',
    component: SeguimientoRepComponent,
    data: {
      defaultSort: 'obraName,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(SeguimientoRepRoute)],
  exports: [RouterModule],
})
export class SeguimientoRepRoutingModule {}
