import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ListaPrecioComponent } from './list/lista-precio.component';
import { ListaPrecioDetailComponent } from './detail/lista-precio-detail.component';
import { ListaPrecioUpdateComponent } from './update/lista-precio-update.component';
import { ListaPrecioSubmitDialogComponent } from './submit/lista-precio-submit-dialog.component';
import { ListaPrecioDeleteDialogComponent } from './delete/lista-precio-delete-dialog.component';
import { ListaPrecioRoutingModule } from './route/lista-precio-routing.module';

@NgModule({
  imports: [SharedModule, ListaPrecioRoutingModule],
  declarations: [
    ListaPrecioComponent,
    ListaPrecioDetailComponent,
    ListaPrecioUpdateComponent,
    ListaPrecioSubmitDialogComponent,
    ListaPrecioDeleteDialogComponent,
  ],
})
export class ListaPrecioModule {}
