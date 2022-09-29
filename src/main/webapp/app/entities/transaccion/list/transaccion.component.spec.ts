import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TransaccionService } from '../service/transaccion.service';

import { TransaccionComponent } from './transaccion.component';

describe('Transaccion Management Component', () => {
  let comp: TransaccionComponent;
  let fixture: ComponentFixture<TransaccionComponent>;
  let service: TransaccionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'transaccion', component: TransaccionComponent }]), HttpClientTestingModule],
      declarations: [TransaccionComponent],
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
      .overrideTemplate(TransaccionComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransaccionComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TransaccionService);

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
    expect(comp.transaccions?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to transaccionService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getTransaccionIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getTransaccionIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
