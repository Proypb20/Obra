import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DetalleListaPrecioService } from '../service/detalle-lista-precio.service';

import { DetalleListaPrecioComponent } from './detalle-lista-precio.component';

describe('DetalleListaPrecio Management Component', () => {
  let comp: DetalleListaPrecioComponent;
  let fixture: ComponentFixture<DetalleListaPrecioComponent>;
  let service: DetalleListaPrecioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'detalle-lista-precio', component: DetalleListaPrecioComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [DetalleListaPrecioComponent],
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
      .overrideTemplate(DetalleListaPrecioComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetalleListaPrecioComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DetalleListaPrecioService);

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
    expect(comp.detalleListaPrecios?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to detalleListaPrecioService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getDetalleListaPrecioIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getDetalleListaPrecioIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
