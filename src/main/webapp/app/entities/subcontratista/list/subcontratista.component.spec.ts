import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SubcontratistaService } from '../service/subcontratista.service';

import { SubcontratistaComponent } from './subcontratista.component';

describe('Subcontratista Management Component', () => {
  let comp: SubcontratistaComponent;
  let fixture: ComponentFixture<SubcontratistaComponent>;
  let service: SubcontratistaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'subcontratista', component: SubcontratistaComponent }]), HttpClientTestingModule],
      declarations: [SubcontratistaComponent],
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
      .overrideTemplate(SubcontratistaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubcontratistaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SubcontratistaService);

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
    expect(comp.subcontratistas?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to subcontratistaService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getSubcontratistaIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getSubcontratistaIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
