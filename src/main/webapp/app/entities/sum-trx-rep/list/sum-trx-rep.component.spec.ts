import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SumTrxRepService } from '../service/sum-trx-rep.service';

import { SumTrxRepComponent } from './sum-trx-rep.component';

describe('SumTrxRep Management Component', () => {
  let comp: SumTrxRepComponent;
  let fixture: ComponentFixture<SumTrxRepComponent>;
  let service: SumTrxRepService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'sumTrxRep', component: SumTrxRepComponent }]), HttpClientTestingModule],
      declarations: [SumTrxRepComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'obra,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'obra,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(SumTrxRepComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SumTrxRepComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SumTrxRepService);

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
    expect(comp.sumTrxRep?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to sumTrxRepService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getSumTrxRepIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getSumTrxRepIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
