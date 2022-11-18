import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SeguimientoRepComponent } from './list/seguimiento-rep.component';
import { SeguimientoRepRoutingModule } from './route/seguimiento-rep-routing.module';
import { UniquePipe } from './unique.pipe';

@NgModule({
  imports: [SharedModule, SeguimientoRepRoutingModule],
  declarations: [SeguimientoRepComponent, UniquePipe],
})
export class SeguimientoRepModule {}
