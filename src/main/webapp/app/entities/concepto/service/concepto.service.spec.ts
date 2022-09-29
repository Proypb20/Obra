import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IConcepto } from '../concepto.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../concepto.test-samples';

import { ConceptoService } from './concepto.service';

const requireRestSample: IConcepto = {
  ...sampleWithRequiredData,
};

describe('Concepto Service', () => {
  let service: ConceptoService;
  let httpMock: HttpTestingController;
  let expectedResult: IConcepto | IConcepto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ConceptoService);
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

    it('should create a Concepto', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const concepto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(concepto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Concepto', () => {
      const concepto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(concepto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Concepto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Concepto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Concepto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addConceptoToCollectionIfMissing', () => {
      it('should add a Concepto to an empty array', () => {
        const concepto: IConcepto = sampleWithRequiredData;
        expectedResult = service.addConceptoToCollectionIfMissing([], concepto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(concepto);
      });

      it('should not add a Concepto to an array that contains it', () => {
        const concepto: IConcepto = sampleWithRequiredData;
        const conceptoCollection: IConcepto[] = [
          {
            ...concepto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addConceptoToCollectionIfMissing(conceptoCollection, concepto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Concepto to an array that doesn't contain it", () => {
        const concepto: IConcepto = sampleWithRequiredData;
        const conceptoCollection: IConcepto[] = [sampleWithPartialData];
        expectedResult = service.addConceptoToCollectionIfMissing(conceptoCollection, concepto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(concepto);
      });

      it('should add only unique Concepto to an array', () => {
        const conceptoArray: IConcepto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const conceptoCollection: IConcepto[] = [sampleWithRequiredData];
        expectedResult = service.addConceptoToCollectionIfMissing(conceptoCollection, ...conceptoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const concepto: IConcepto = sampleWithRequiredData;
        const concepto2: IConcepto = sampleWithPartialData;
        expectedResult = service.addConceptoToCollectionIfMissing([], concepto, concepto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(concepto);
        expect(expectedResult).toContain(concepto2);
      });

      it('should accept null and undefined values', () => {
        const concepto: IConcepto = sampleWithRequiredData;
        expectedResult = service.addConceptoToCollectionIfMissing([], null, concepto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(concepto);
      });

      it('should return initial array if no Concepto is added', () => {
        const conceptoCollection: IConcepto[] = [sampleWithRequiredData];
        expectedResult = service.addConceptoToCollectionIfMissing(conceptoCollection, undefined, null);
        expect(expectedResult).toEqual(conceptoCollection);
      });
    });

    describe('compareConcepto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareConcepto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareConcepto(entity1, entity2);
        const compareResult2 = service.compareConcepto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareConcepto(entity1, entity2);
        const compareResult2 = service.compareConcepto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareConcepto(entity1, entity2);
        const compareResult2 = service.compareConcepto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
