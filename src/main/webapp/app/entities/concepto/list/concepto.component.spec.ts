import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ConceptoService } from '../service/concepto.service';

import { ConceptoComponent } from './concepto.component';

describe('Concepto Management Component', () => {
  let comp: ConceptoComponent;
  let fixture: ComponentFixture<ConceptoComponent>;
  let service: ConceptoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'concepto', component: ConceptoComponent }]), HttpClientTestingModule],
      declarations: [ConceptoComponent],
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
      .overrideTemplate(ConceptoComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConceptoComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ConceptoService);

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
    expect(comp.conceptos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to conceptoService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getConceptoIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getConceptoIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
