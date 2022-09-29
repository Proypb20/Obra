import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetalleListaPrecio } from '../detalle-lista-precio.model';
import { DetalleListaPrecioService } from '../service/detalle-lista-precio.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './detalle-lista-precio-delete-dialog.component.html',
})
export class DetalleListaPrecioDeleteDialogComponent {
  detalleListaPrecio?: IDetalleListaPrecio;

  constructor(protected detalleListaPrecioService: DetalleListaPrecioService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detalleListaPrecioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
