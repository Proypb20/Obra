import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DetalleAcopioService } from '../service/detalle-acopio.service';

import { DetalleAcopioComponent } from './detalle-acopio.component';

describe('DetalleAcopio Management Component', () => {
  let comp: DetalleAcopioComponent;
  let fixture: ComponentFixture<DetalleAcopioComponent>;
  let service: DetalleAcopioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'detalle-acopio', component: DetalleAcopioComponent }]), HttpClientTestingModule],
      declarations: [DetalleAcopioComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(DetalleAcopioComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetalleAcopioComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DetalleAcopioService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.detalleAcopios?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to detalleAcopioService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getDetalleAcopioIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getDetalleAcopioIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
