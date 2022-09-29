import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AcopioComponent } from './list/acopio.component';
import { AcopioDetailComponent } from './detail/acopio-detail.component';
import { AcopioUpdateComponent } from './update/acopio-update.component';
import { AcopioDeleteDialogComponent } from './delete/acopio-delete-dialog.component';
import { AcopioRoutingModule } from './route/acopio-routing.module';

@NgModule({
  imports: [SharedModule, AcopioRoutingModule],
  declarations: [AcopioComponent, AcopioDetailComponent, AcopioUpdateComponent, AcopioDeleteDialogComponent],
})
export class AcopioModule {}
