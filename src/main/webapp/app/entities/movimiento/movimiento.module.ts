import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MovimientoComponent } from './list/movimiento.component';
import { MovimientoDetailComponent } from './detail/movimiento-detail.component';
import { MovimientoUpdateComponent } from './update/movimiento-update.component';
import { MovimientoDeleteDialogComponent } from './delete/movimiento-delete-dialog.component';
import { MovimientoRoutingModule } from './route/movimiento-routing.module';

@NgModule({
  imports: [SharedModule, MovimientoRoutingModule],
  declarations: [MovimientoComponent, MovimientoDetailComponent, MovimientoUpdateComponent, MovimientoDeleteDialogComponent],
})
export class MovimientoModule {}
