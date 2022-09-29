import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TareaComponent } from './list/tarea.component';
import { TareaDetailComponent } from './detail/tarea-detail.component';
import { TareaUpdateComponent } from './update/tarea-update.component';
import { TareaDeleteDialogComponent } from './delete/tarea-delete-dialog.component';
import { TareaRoutingModule } from './route/tarea-routing.module';

@NgModule({
  imports: [SharedModule, TareaRoutingModule],
  declarations: [TareaComponent, TareaDetailComponent, TareaUpdateComponent, TareaDeleteDialogComponent],
})
export class TareaModule {}
