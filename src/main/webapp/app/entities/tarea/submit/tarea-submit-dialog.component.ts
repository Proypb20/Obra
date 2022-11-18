import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { TareaService } from '../service/tarea.service';
import { map } from 'rxjs/operators';

@Component({
  templateUrl: './tarea-submit-dialog.component.html',
})
export class TareaSubmitDialogComponent {
  selectedFiles?: FileList;

  constructor(protected tareaService: TareaService, protected activeModal: NgbActiveModal, protected fb: FormBuilder) {}

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
        this.tareaService.submit(file).subscribe(() => {
          this.activeModal.close('yes');
        });
      }
    }
  }
}
