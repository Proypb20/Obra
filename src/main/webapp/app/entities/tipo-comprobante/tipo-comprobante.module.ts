import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TipoComprobanteComponent } from './list/tipo-comprobante.component';
import { TipoComprobanteDetailComponent } from './detail/tipo-comprobante-detail.component';
import { TipoComprobanteUpdateComponent } from './update/tipo-comprobante-update.component';
import { TipoComprobanteDeleteDialogComponent } from './delete/tipo-comprobante-delete-dialog.component';
import { TipoComprobanteRoutingModule } from './route/tipo-comprobante-routing.module';

@NgModule({
  imports: [SharedModule, TipoComprobanteRoutingModule],
  declarations: [
    TipoComprobanteComponent,
    TipoComprobanteDetailComponent,
    TipoComprobanteUpdateComponent,
    TipoComprobanteDeleteDialogComponent,
  ],
})
export class TipoComprobanteModule {}
