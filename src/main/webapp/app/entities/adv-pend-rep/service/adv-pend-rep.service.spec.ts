import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdvPendRep } from '../adv-pending-rep.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ad-pend-rep.test-samples';

import { AdvPendRepService } from './adv-pending-rep.service';

const requireRestSample: IAdvPendRep = {
  ...sampleWithRequiredData,
};

describe('AdvPendRep Service', () => {
  let service: AdvPendRepService;
  let httpMock: HttpTestingController;
  let expectedResult: IAdvPendRep | IAdvPendRep[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdvPendRepService);
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

    it('should create a AdvPendRep', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const advPendRep = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(advPendRep).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdvPendRep', () => {
      const advPendRep = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(advPendRep).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdvPendRep', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdvPendRep', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AdvPendRep', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAdvPendRepToCollectionIfMissing', () => {
      it('should add a AdvPendRep to an empty array', () => {
        const advPendRep: IAdvPendRep = sampleWithRequiredData;
        expectedResult = service.addAdvPendRepToCollectionIfMissing([], advPendRep);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(advPendRep);
      });

      it('should not add a AdvPendRep to an array that contains it', () => {
        const advPendRep: IAdvPendRep = sampleWithRequiredData;
        const advPendRepCollection: IAdvPendRep[] = [
          {
            ...advPendRep,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAdvPendRepToCollectionIfMissing(advPendRepCollection, advPendRep);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdvPendRep to an array that doesn't contain it", () => {
        const advPendRep: IAdvPendRep = sampleWithRequiredData;
        const advPendRepCollection: IAdvPendRep[] = [sampleWithPartialData];
        expectedResult = service.addAdvPendRepToCollectionIfMissing(advPendRepCollection, advPendRep);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(advPendRep);
      });

      it('should add only unique AdvPendRep to an array', () => {
        const advPendRepArray: IAdvPendRep[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const advPendRepCollection: IAdvPendRep[] = [sampleWithRequiredData];
        expectedResult = service.addAdvPendRepToCollectionIfMissing(advPendRepCollection, ...advPendRepArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const advPendRep: IAdvPendRep = sampleWithRequiredData;
        const advPendRep2: IAdvPendRep = sampleWithPartialData;
        expectedResult = service.addAdvPendRepToCollectionIfMissing([], advPendRep, advPendRep2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(advPendRep);
        expect(expectedResult).toContain(advPendRep2);
      });

      it('should accept null and undefined values', () => {
        const advPendRep: IAdvPendRep = sampleWithRequiredData;
        expectedResult = service.addAdvPendRepToCollectionIfMissing([], null, advPendRep, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(advPendRep);
      });

      it('should return initial array if no AdvPendRep is added', () => {
        const advPendRepCollection: IAdvPendRep[] = [sampleWithRequiredData];
        expectedResult = service.addAdvPendRepToCollectionIfMissing(advPendRepCollection, undefined, null);
        expect(expectedResult).toEqual(advPendRepCollection);
      });
    });

    describe('compareAdvPendRep', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAdvPendRep(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAdvPendRep(entity1, entity2);
        const compareResult2 = service.compareAdvPendRep(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAdvPendRep(entity1, entity2);
        const compareResult2 = service.compareAdvPendRep(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAdvPendRep(entity1, entity2);
        const compareResult2 = service.compareAdvPendRep(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
