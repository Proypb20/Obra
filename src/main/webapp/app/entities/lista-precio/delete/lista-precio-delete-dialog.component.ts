import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IListaPrecio } from '../lista-precio.model';
import { ListaPrecioService } from '../service/lista-precio.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './lista-precio-delete-dialog.component.html',
})
export class ListaPrecioDeleteDialogComponent {
  listaPrecio?: IListaPrecio;

  constructor(protected listaPrecioService: ListaPrecioService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.listaPrecioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
