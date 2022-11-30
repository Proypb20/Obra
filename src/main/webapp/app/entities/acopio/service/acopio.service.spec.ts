import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAcopio } from '../acopio.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../acopio.test-samples';

import { AcopioService, RestAcopio } from './acopio.service';

const requireRestSample: RestAcopio = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.toJSON(),
};

describe('Acopio Service', () => {
  let service: AcopioService;
  let httpMock: HttpTestingController;
  let expectedResult: IAcopio | IAcopio[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AcopioService);
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

    it('should create a Acopio', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const acopio = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(acopio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Acopio', () => {
      const acopio = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(acopio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Acopio', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Acopio', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Acopio', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAcopioToCollectionIfMissing', () => {
      it('should add a Acopio to an empty array', () => {
        const acopio: IAcopio = sampleWithRequiredData;
        expectedResult = service.addAcopioToCollectionIfMissing([], acopio);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(acopio);
      });

      it('should not add a Acopio to an array that contains it', () => {
        const acopio: IAcopio = sampleWithRequiredData;
        const acopioCollection: IAcopio[] = [
          {
            ...acopio,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAcopioToCollectionIfMissing(acopioCollection, acopio);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Acopio to an array that doesn't contain it", () => {
        const acopio: IAcopio = sampleWithRequiredData;
        const acopioCollection: IAcopio[] = [sampleWithPartialData];
        expectedResult = service.addAcopioToCollectionIfMissing(acopioCollection, acopio);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(acopio);
      });

      it('should add only unique Acopio to an array', () => {
        const acopioArray: IAcopio[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const acopioCollection: IAcopio[] = [sampleWithRequiredData];
        expectedResult = service.addAcopioToCollectionIfMissing(acopioCollection, ...acopioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const acopio: IAcopio = sampleWithRequiredData;
        const acopio2: IAcopio = sampleWithPartialData;
        expectedResult = service.addAcopioToCollectionIfMissing([], acopio, acopio2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(acopio);
        expect(expectedResult).toContain(acopio2);
      });

      it('should accept null and undefined values', () => {
        const acopio: IAcopio = sampleWithRequiredData;
        expectedResult = service.addAcopioToCollectionIfMissing([], null, acopio, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(acopio);
      });

      it('should return initial array if no Acopio is added', () => {
        const acopioCollection: IAcopio[] = [sampleWithRequiredData];
        expectedResult = service.addAcopioToCollectionIfMissing(acopioCollection, undefined, null);
        expect(expectedResult).toEqual(acopioCollection);
      });
    });

    describe('compareAcopio', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAcopio(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAcopio(entity1, entity2);
        const compareResult2 = service.compareAcopio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAcopio(entity1, entity2);
        const compareResult2 = service.compareAcopio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAcopio(entity1, entity2);
        const compareResult2 = service.compareAcopio(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
