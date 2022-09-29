import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TipoComprobanteService } from '../service/tipo-comprobante.service';

import { TipoComprobanteComponent } from './tipo-comprobante.component';

describe('TipoComprobante Management Component', () => {
  let comp: TipoComprobanteComponent;
  let fixture: ComponentFixture<TipoComprobanteComponent>;
  let service: TipoComprobanteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'tipo-comprobante', component: TipoComprobanteComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [TipoComprobanteComponent],
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
      .overrideTemplate(TipoComprobanteComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TipoComprobanteComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TipoComprobanteService);

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
    expect(comp.tipoComprobantes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to tipoComprobanteService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getTipoComprobanteIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getTipoComprobanteIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
