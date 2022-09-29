import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetalleAcopio } from '../detalle-acopio.model';
import { DetalleAcopioService } from '../service/detalle-acopio.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './detalle-acopio-delete-dialog.component.html',
})
export class DetalleAcopioDeleteDialogComponent {
  detalleAcopio?: IDetalleAcopio;

  constructor(protected detalleAcopioService: DetalleAcopioService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detalleAcopioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
