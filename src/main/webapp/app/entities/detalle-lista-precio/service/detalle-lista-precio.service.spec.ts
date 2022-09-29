import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDetalleListaPrecio } from '../detalle-lista-precio.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../detalle-lista-precio.test-samples';

import { DetalleListaPrecioService } from './detalle-lista-precio.service';

const requireRestSample: IDetalleListaPrecio = {
  ...sampleWithRequiredData,
};

describe('DetalleListaPrecio Service', () => {
  let service: DetalleListaPrecioService;
  let httpMock: HttpTestingController;
  let expectedResult: IDetalleListaPrecio | IDetalleListaPrecio[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DetalleListaPrecioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a DetalleListaPrecio', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const detalleListaPrecio = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(detalleListaPrecio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DetalleListaPrecio', () => {
      const detalleListaPrecio = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(detalleListaPrecio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DetalleListaPrecio', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DetalleListaPrecio', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DetalleListaPrecio', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDetalleListaPrecioToCollectionIfMissing', () => {
      it('should add a DetalleListaPrecio to an empty array', () => {
        const detalleListaPrecio: IDetalleListaPrecio = sampleWithRequiredData;
        expectedResult = service.addDetalleListaPrecioToCollectionIfMissing([], detalleListaPrecio);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detalleListaPrecio);
      });

      it('should not add a DetalleListaPrecio to an array that contains it', () => {
        const detalleListaPrecio: IDetalleListaPrecio = sampleWithRequiredData;
        const detalleListaPrecioCollection: IDetalleListaPrecio[] = [
          {
            ...detalleListaPrecio,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDetalleListaPrecioToCollectionIfMissing(detalleListaPrecioCollection, detalleListaPrecio);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DetalleListaPrecio to an array that doesn't contain it", () => {
        const detalleListaPrecio: IDetalleListaPrecio = sampleWithRequiredData;
        const detalleListaPrecioCollection: IDetalleListaPrecio[] = [sampleWithPartialData];
        expectedResult = service.addDetalleListaPrecioToCollectionIfMissing(detalleListaPrecioCollection, detalleListaPrecio);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detalleListaPrecio);
      });

      it('should add only unique DetalleListaPrecio to an array', () => {
        const detalleListaPrecioArray: IDetalleListaPrecio[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const detalleListaPrecioCollection: IDetalleListaPrecio[] = [sampleWithRequiredData];
        expectedResult = service.addDetalleListaPrecioToCollectionIfMissing(detalleListaPrecioCollection, ...detalleListaPrecioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const detalleListaPrecio: IDetalleListaPrecio = sampleWithRequiredData;
        const detalleListaPrecio2: IDetalleListaPrecio = sampleWithPartialData;
        expectedResult = service.addDetalleListaPrecioToCollectionIfMissing([], detalleListaPrecio, detalleListaPrecio2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detalleListaPrecio);
        expect(expectedResult).toContain(detalleListaPrecio2);
      });

      it('should accept null and undefined values', () => {
        const detalleListaPrecio: IDetalleListaPrecio = sampleWithRequiredData;
        expectedResult = service.addDetalleListaPrecioToCollectionIfMissing([], null, detalleListaPrecio, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detalleListaPrecio);
      });

      it('should return initial array if no DetalleListaPrecio is added', () => {
        const detalleListaPrecioCollection: IDetalleListaPrecio[] = [sampleWithRequiredData];
        expectedResult = service.addDetalleListaPrecioToCollectionIfMissing(detalleListaPrecioCollection, undefined, null);
        expect(expectedResult).toEqual(detalleListaPrecioCollection);
      });
    });

    describe('compareDetalleListaPrecio', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDetalleListaPrecio(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDetalleListaPrecio(entity1, entity2);
        const compareResult2 = service.compareDetalleListaPrecio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDetalleListaPrecio(entity1, entity2);
        const compareResult2 = service.compareDetalleListaPrecio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDetalleListaPrecio(entity1, entity2);
        const compareResult2 = service.compareDetalleListaPrecio(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
