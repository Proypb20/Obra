import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDetalleAcopio } from '../detalle-acopio.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../detalle-acopio.test-samples';

import { DetalleAcopioService, RestDetalleAcopio } from './detalle-acopio.service';

const requireRestSample: RestDetalleAcopio = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
  requestDate: sampleWithRequiredData.requestDate?.format(DATE_FORMAT),
  promiseDate: sampleWithRequiredData.promiseDate?.format(DATE_FORMAT),
};

describe('DetalleAcopio Service', () => {
  let service: DetalleAcopioService;
  let httpMock: HttpTestingController;
  let expectedResult: IDetalleAcopio | IDetalleAcopio[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DetalleAcopioService);
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

    it('should create a DetalleAcopio', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const detalleAcopio = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(detalleAcopio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DetalleAcopio', () => {
      const detalleAcopio = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(detalleAcopio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DetalleAcopio', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DetalleAcopio', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DetalleAcopio', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDetalleAcopioToCollectionIfMissing', () => {
      it('should add a DetalleAcopio to an empty array', () => {
        const detalleAcopio: IDetalleAcopio = sampleWithRequiredData;
        expectedResult = service.addDetalleAcopioToCollectionIfMissing([], detalleAcopio);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detalleAcopio);
      });

      it('should not add a DetalleAcopio to an array that contains it', () => {
        const detalleAcopio: IDetalleAcopio = sampleWithRequiredData;
        const detalleAcopioCollection: IDetalleAcopio[] = [
          {
            ...detalleAcopio,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDetalleAcopioToCollectionIfMissing(detalleAcopioCollection, detalleAcopio);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DetalleAcopio to an array that doesn't contain it", () => {
        const detalleAcopio: IDetalleAcopio = sampleWithRequiredData;
        const detalleAcopioCollection: IDetalleAcopio[] = [sampleWithPartialData];
        expectedResult = service.addDetalleAcopioToCollectionIfMissing(detalleAcopioCollection, detalleAcopio);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detalleAcopio);
      });

      it('should add only unique DetalleAcopio to an array', () => {
        const detalleAcopioArray: IDetalleAcopio[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const detalleAcopioCollection: IDetalleAcopio[] = [sampleWithRequiredData];
        expectedResult = service.addDetalleAcopioToCollectionIfMissing(detalleAcopioCollection, ...detalleAcopioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const detalleAcopio: IDetalleAcopio = sampleWithRequiredData;
        const detalleAcopio2: IDetalleAcopio = sampleWithPartialData;
        expectedResult = service.addDetalleAcopioToCollectionIfMissing([], detalleAcopio, detalleAcopio2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detalleAcopio);
        expect(expectedResult).toContain(detalleAcopio2);
      });

      it('should accept null and undefined values', () => {
        const detalleAcopio: IDetalleAcopio = sampleWithRequiredData;
        expectedResult = service.addDetalleAcopioToCollectionIfMissing([], null, detalleAcopio, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detalleAcopio);
      });

      it('should return initial array if no DetalleAcopio is added', () => {
        const detalleAcopioCollection: IDetalleAcopio[] = [sampleWithRequiredData];
        expectedResult = service.addDetalleAcopioToCollectionIfMissing(detalleAcopioCollection, undefined, null);
        expect(expectedResult).toEqual(detalleAcopioCollection);
      });
    });

    describe('compareDetalleAcopio', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDetalleAcopio(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDetalleAcopio(entity1, entity2);
        const compareResult2 = service.compareDetalleAcopio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDetalleAcopio(entity1, entity2);
        const compareResult2 = service.compareDetalleAcopio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDetalleAcopio(entity1, entity2);
        const compareResult2 = service.compareDetalleAcopio(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
