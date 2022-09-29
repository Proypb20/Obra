import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUnidadMedida } from '../unidad-medida.model';
import { UnidadMedidaService } from '../service/unidad-medida.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './unidad-medida-delete-dialog.component.html',
})
export class UnidadMedidaDeleteDialogComponent {
  unidadMedida?: IUnidadMedida;

  constructor(protected unidadMedidaService: UnidadMedidaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.unidadMedidaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
