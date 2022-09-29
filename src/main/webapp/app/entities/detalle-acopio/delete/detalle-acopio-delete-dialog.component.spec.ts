jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { DetalleAcopioService } from '../service/detalle-acopio.service';

import { DetalleAcopioDeleteDialogComponent } from './detalle-acopio-delete-dialog.component';

describe('DetalleAcopio Management Delete Component', () => {
  let comp: DetalleAcopioDeleteDialogComponent;
  let fixture: ComponentFixture<DetalleAcopioDeleteDialogComponent>;
  let service: DetalleAcopioService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DetalleAcopioDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(DetalleAcopioDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DetalleAcopioDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DetalleAcopioService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
