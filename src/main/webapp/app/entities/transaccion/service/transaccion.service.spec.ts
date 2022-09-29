import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITransaccion } from '../transaccion.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../transaccion.test-samples';

import { TransaccionService, RestTransaccion } from './transaccion.service';

const requireRestSample: RestTransaccion = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
};

describe('Transaccion Service', () => {
  let service: TransaccionService;
  let httpMock: HttpTestingController;
  let expectedResult: ITransaccion | ITransaccion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TransaccionService);
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

    it('should create a Transaccion', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const transaccion = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(transaccion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Transaccion', () => {
      const transaccion = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(transaccion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Transaccion', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Transaccion', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Transaccion', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTransaccionToCollectionIfMissing', () => {
      it('should add a Transaccion to an empty array', () => {
        const transaccion: ITransaccion = sampleWithRequiredData;
        expectedResult = service.addTransaccionToCollectionIfMissing([], transaccion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transaccion);
      });

      it('should not add a Transaccion to an array that contains it', () => {
        const transaccion: ITransaccion = sampleWithRequiredData;
        const transaccionCollection: ITransaccion[] = [
          {
            ...transaccion,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTransaccionToCollectionIfMissing(transaccionCollection, transaccion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Transaccion to an array that doesn't contain it", () => {
        const transaccion: ITransaccion = sampleWithRequiredData;
        const transaccionCollection: ITransaccion[] = [sampleWithPartialData];
        expectedResult = service.addTransaccionToCollectionIfMissing(transaccionCollection, transaccion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transaccion);
      });

      it('should add only unique Transaccion to an array', () => {
        const transaccionArray: ITransaccion[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const transaccionCollection: ITransaccion[] = [sampleWithRequiredData];
        expectedResult = service.addTransaccionToCollectionIfMissing(transaccionCollection, ...transaccionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transaccion: ITransaccion = sampleWithRequiredData;
        const transaccion2: ITransaccion = sampleWithPartialData;
        expectedResult = service.addTransaccionToCollectionIfMissing([], transaccion, transaccion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transaccion);
        expect(expectedResult).toContain(transaccion2);
      });

      it('should accept null and undefined values', () => {
        const transaccion: ITransaccion = sampleWithRequiredData;
        expectedResult = service.addTransaccionToCollectionIfMissing([], null, transaccion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transaccion);
      });

      it('should return initial array if no Transaccion is added', () => {
        const transaccionCollection: ITransaccion[] = [sampleWithRequiredData];
        expectedResult = service.addTransaccionToCollectionIfMissing(transaccionCollection, undefined, null);
        expect(expectedResult).toEqual(transaccionCollection);
      });
    });

    describe('compareTransaccion', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTransaccion(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTransaccion(entity1, entity2);
        const compareResult2 = service.compareTransaccion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTransaccion(entity1, entity2);
        const compareResult2 = service.compareTransaccion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTransaccion(entity1, entity2);
        const compareResult2 = service.compareTransaccion(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
