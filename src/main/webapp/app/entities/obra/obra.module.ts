import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ObraComponent } from './list/obra.component';
import { ObraDetailComponent } from './detail/obra-detail.component';
import { ObraUpdateComponent } from './update/obra-update.component';
import { ObraDeleteDialogComponent } from './delete/obra-delete-dialog.component';
import { ObraRoutingModule } from './route/obra-routing.module';

@NgModule({
  imports: [SharedModule, ObraRoutingModule],
  declarations: [ObraComponent, ObraDetailComponent, ObraUpdateComponent, ObraDeleteDialogComponent],
})
export class ObraModule {}
