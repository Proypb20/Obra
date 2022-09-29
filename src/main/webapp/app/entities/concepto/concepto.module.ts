import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ConceptoComponent } from './list/concepto.component';
import { ConceptoDetailComponent } from './detail/concepto-detail.component';
import { ConceptoUpdateComponent } from './update/concepto-update.component';
import { ConceptoDeleteDialogComponent } from './delete/concepto-delete-dialog.component';
import { ConceptoRoutingModule } from './route/concepto-routing.module';

@NgModule({
  imports: [SharedModule, ConceptoRoutingModule],
  declarations: [ConceptoComponent, ConceptoDetailComponent, ConceptoUpdateComponent, ConceptoDeleteDialogComponent],
})
export class ConceptoModule {}
