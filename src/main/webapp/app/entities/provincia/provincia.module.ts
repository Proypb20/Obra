import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProvinciaComponent } from './list/provincia.component';
import { ProvinciaDetailComponent } from './detail/provincia-detail.component';
import { ProvinciaUpdateComponent } from './update/provincia-update.component';
import { ProvinciaDeleteDialogComponent } from './delete/provincia-delete-dialog.component';
import { ProvinciaRoutingModule } from './route/provincia-routing.module';

@NgModule({
  imports: [SharedModule, ProvinciaRoutingModule],
  declarations: [ProvinciaComponent, ProvinciaDetailComponent, ProvinciaUpdateComponent, ProvinciaDeleteDialogComponent],
})
export class ProvinciaModule {}
