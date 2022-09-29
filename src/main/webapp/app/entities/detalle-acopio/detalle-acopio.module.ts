import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DetalleAcopioComponent } from './list/detalle-acopio.component';
import { DetalleAcopioDetailComponent } from './detail/detalle-acopio-detail.component';
import { DetalleAcopioUpdateComponent } from './update/detalle-acopio-update.component';
import { DetalleAcopioDeleteDialogComponent } from './delete/detalle-acopio-delete-dialog.component';
import { DetalleAcopioRoutingModule } from './route/detalle-acopio-routing.module';

@NgModule({
  imports: [SharedModule, DetalleAcopioRoutingModule],
  declarations: [DetalleAcopioComponent, DetalleAcopioDetailComponent, DetalleAcopioUpdateComponent, DetalleAcopioDeleteDialogComponent],
})
export class DetalleAcopioModule {}
