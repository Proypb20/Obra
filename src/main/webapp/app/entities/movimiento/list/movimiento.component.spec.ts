import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MovimientoService } from '../service/movimiento.service';

import { MovimientoComponent } from './movimiento.component';

describe('Movimiento Management Component', () => {
  let comp: MovimientoComponent;
  let fixture: ComponentFixture<MovimientoComponent>;
  let service: MovimientoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'movimiento', component: MovimientoComponent }]), HttpClientTestingModule],
      declarations: [MovimientoComponent],
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
      .overrideTemplate(MovimientoComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MovimientoComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MovimientoService);

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
    expect(comp.movimientos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to movimientoService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getMovimientoIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMovimientoIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
