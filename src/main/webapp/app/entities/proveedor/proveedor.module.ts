import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProveedorComponent } from './list/proveedor.component';
import { ProveedorDetailComponent } from './detail/proveedor-detail.component';
import { ProveedorUpdateComponent } from './update/proveedor-update.component';
import { ProveedorDeleteDialogComponent } from './delete/proveedor-delete-dialog.component';
import { ProveedorRoutingModule } from './route/proveedor-routing.module';

@NgModule({
  imports: [SharedModule, ProveedorRoutingModule],
  declarations: [ProveedorComponent, ProveedorDetailComponent, ProveedorUpdateComponent, ProveedorDeleteDialogComponent],
})
export class ProveedorModule {}
