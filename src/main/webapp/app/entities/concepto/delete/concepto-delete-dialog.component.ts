import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IConcepto } from '../concepto.model';
import { ConceptoService } from '../service/concepto.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './concepto-delete-dialog.component.html',
})
export class ConceptoDeleteDialogComponent {
  concepto?: IConcepto;

  constructor(protected conceptoService: ConceptoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
