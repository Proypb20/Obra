import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { HttpResponse } from '@angular/common/http';
import { ListaPrecioService } from '../service/lista-precio.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';
import { finalize, map } from 'rxjs/operators';

@Component({
  templateUrl: './lista-precio-submit-dialog.component.html',
})
export class ListaPrecioSubmitDialogComponent {
  selectedFiles?: FileList;

  proveedorsSharedCollection: IProveedor[] = [];
  proveedor?: IProveedor;

  constructor(
    protected listaPrecioService: ListaPrecioService,
    protected activeModal: NgbActiveModal,
    protected proveedorService: ProveedorService
  ) {}

  ngOnInit(): void {
    this.loadRelationshipsOptions();
  }

  compareProveedor = (o1: IProveedor | null, o2: IProveedor | null): boolean => this.proveedorService.compareProveedor(o1, o2);

  selectFile(listaPrecio: any): void {
    this.selectedFiles = listaPrecio.target.files;
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  upload(): void {
    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);
      if (file) {
        this.listaPrecioService.submit(file).subscribe(() => {
          this.activeModal.close('yes');
        });
      }
    }
  }

  protected loadRelationshipsOptions(): void {
    this.proveedorService
      .query()
      .pipe(map((res: HttpResponse<IProveedor[]>) => res.body ?? []))
      .pipe(
        map((proveedors: IProveedor[]) => this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(proveedors, this.proveedor))
      )
      .subscribe((proveedors: IProveedor[]) => (this.proveedorsSharedCollection = proveedors));
  }
}
