import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DetalleListaPrecioComponent } from './list/detalle-lista-precio.component';
import { DetalleListaPrecioDetailComponent } from './detail/detalle-lista-precio-detail.component';
import { DetalleListaPrecioUpdateComponent } from './update/detalle-lista-precio-update.component';
import { DetalleListaPrecioDeleteDialogComponent } from './delete/detalle-lista-precio-delete-dialog.component';
import { DetalleListaPrecioRoutingModule } from './route/detalle-lista-precio-routing.module';

@NgModule({
  imports: [SharedModule, DetalleListaPrecioRoutingModule],
  declarations: [
    DetalleListaPrecioComponent,
    DetalleListaPrecioDetailComponent,
    DetalleListaPrecioUpdateComponent,
    DetalleListaPrecioDeleteDialogComponent,
  ],
})
export class DetalleListaPrecioModule {}
