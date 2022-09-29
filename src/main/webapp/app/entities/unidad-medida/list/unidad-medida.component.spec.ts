import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { UnidadMedidaService } from '../service/unidad-medida.service';

import { UnidadMedidaComponent } from './unidad-medida.component';

describe('UnidadMedida Management Component', () => {
  let comp: UnidadMedidaComponent;
  let fixture: ComponentFixture<UnidadMedidaComponent>;
  let service: UnidadMedidaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'unidad-medida', component: UnidadMedidaComponent }]), HttpClientTestingModule],
      declarations: [UnidadMedidaComponent],
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
      .overrideTemplate(UnidadMedidaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UnidadMedidaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UnidadMedidaService);

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
    expect(comp.unidadMedidas?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to unidadMedidaService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getUnidadMedidaIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getUnidadMedidaIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
