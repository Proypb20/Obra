import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UnidadMedidaComponent } from './list/unidad-medida.component';
import { UnidadMedidaDetailComponent } from './detail/unidad-medida-detail.component';
import { UnidadMedidaUpdateComponent } from './update/unidad-medida-update.component';
import { UnidadMedidaDeleteDialogComponent } from './delete/unidad-medida-delete-dialog.component';
import { UnidadMedidaRoutingModule } from './route/unidad-medida-routing.module';

@NgModule({
  imports: [SharedModule, UnidadMedidaRoutingModule],
  declarations: [UnidadMedidaComponent, UnidadMedidaDetailComponent, UnidadMedidaUpdateComponent, UnidadMedidaDeleteDialogComponent],
})
export class UnidadMedidaModule {}
