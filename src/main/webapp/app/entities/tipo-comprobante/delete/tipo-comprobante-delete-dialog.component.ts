import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoComprobante } from '../tipo-comprobante.model';
import { TipoComprobanteService } from '../service/tipo-comprobante.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './tipo-comprobante-delete-dialog.component.html',
})
export class TipoComprobanteDeleteDialogComponent {
  tipoComprobante?: ITipoComprobante;

  constructor(protected tipoComprobanteService: TipoComprobanteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoComprobanteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
