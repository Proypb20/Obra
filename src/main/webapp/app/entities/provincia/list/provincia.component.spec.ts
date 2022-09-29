import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProvinciaService } from '../service/provincia.service';

import { ProvinciaComponent } from './provincia.component';

describe('Provincia Management Component', () => {
  let comp: ProvinciaComponent;
  let fixture: ComponentFixture<ProvinciaComponent>;
  let service: ProvinciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'provincia', component: ProvinciaComponent }]), HttpClientTestingModule],
      declarations: [ProvinciaComponent],
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
      .overrideTemplate(ProvinciaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProvinciaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProvinciaService);

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
    expect(comp.provincias?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to provinciaService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getProvinciaIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getProvinciaIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
