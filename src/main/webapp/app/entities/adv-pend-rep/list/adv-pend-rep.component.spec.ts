import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AdvPendRepService } from '../service/adv-pend-rep.service';

import { AdvPendRepComponent } from './adv-pend-rep.component';

describe('AdvPendRep Management Component', () => {
  let comp: AdvPendRepComponent;
  let fixture: ComponentFixture<AdvPendRepComponent>;
  let service: AdvPendRepService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'advPendRep', component: AdvPendRepComponent }]), HttpClientTestingModule],
      declarations: [AdvPendRepComponent],
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
      .overrideTemplate(AdvPendRepComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdvPendRepComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AdvPendRepService);

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
    expect(comp.advPendRep?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to advPendRepService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAdvPendRepIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAdvPendRepIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
