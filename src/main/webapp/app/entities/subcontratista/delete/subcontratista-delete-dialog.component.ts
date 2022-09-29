import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubcontratista } from '../subcontratista.model';
import { SubcontratistaService } from '../service/subcontratista.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './subcontratista-delete-dialog.component.html',
})
export class SubcontratistaDeleteDialogComponent {
  subcontratista?: ISubcontratista;

  constructor(protected subcontratistaService: SubcontratistaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subcontratistaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
