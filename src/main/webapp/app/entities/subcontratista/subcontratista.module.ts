import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SubcontratistaComponent } from './list/subcontratista.component';
import { SubcontratistaDetailComponent } from './detail/subcontratista-detail.component';
import { SubcontratistaUpdateComponent } from './update/subcontratista-update.component';
import { SubcontratistaDeleteDialogComponent } from './delete/subcontratista-delete-dialog.component';
import { SubcontratistaRoutingModule } from './route/subcontratista-routing.module';

@NgModule({
  imports: [SharedModule, SubcontratistaRoutingModule],
  declarations: [
    SubcontratistaComponent,
    SubcontratistaDetailComponent,
    SubcontratistaUpdateComponent,
    SubcontratistaDeleteDialogComponent,
  ],
})
export class SubcontratistaModule {}
