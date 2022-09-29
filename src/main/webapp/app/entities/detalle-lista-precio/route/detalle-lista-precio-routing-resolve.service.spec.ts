import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDetalleListaPrecio } from '../detalle-lista-precio.model';
import { DetalleListaPrecioService } from '../service/detalle-lista-precio.service';

import { DetalleListaPrecioRoutingResolveService } from './detalle-lista-precio-routing-resolve.service';

describe('DetalleListaPrecio routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DetalleListaPrecioRoutingResolveService;
  let service: DetalleListaPrecioService;
  let resultDetalleListaPrecio: IDetalleListaPrecio | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(DetalleListaPrecioRoutingResolveService);
    service = TestBed.inject(DetalleListaPrecioService);
    resultDetalleListaPrecio = undefined;
  });

  describe('resolve', () => {
    it('should return IDetalleListaPrecio returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetalleListaPrecio = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDetalleListaPrecio).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetalleListaPrecio = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDetalleListaPrecio).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDetalleListaPrecio>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetalleListaPrecio = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDetalleListaPrecio).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
