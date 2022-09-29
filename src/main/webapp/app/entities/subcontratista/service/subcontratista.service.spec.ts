import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISubcontratista } from '../subcontratista.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../subcontratista.test-samples';

import { SubcontratistaService } from './subcontratista.service';

const requireRestSample: ISubcontratista = {
  ...sampleWithRequiredData,
};

describe('Subcontratista Service', () => {
  let service: SubcontratistaService;
  let httpMock: HttpTestingController;
  let expectedResult: ISubcontratista | ISubcontratista[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SubcontratistaService);
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

    it('should create a Subcontratista', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const subcontratista = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(subcontratista).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Subcontratista', () => {
      const subcontratista = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(subcontratista).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Subcontratista', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Subcontratista', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Subcontratista', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSubcontratistaToCollectionIfMissing', () => {
      it('should add a Subcontratista to an empty array', () => {
        const subcontratista: ISubcontratista = sampleWithRequiredData;
        expectedResult = service.addSubcontratistaToCollectionIfMissing([], subcontratista);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subcontratista);
      });

      it('should not add a Subcontratista to an array that contains it', () => {
        const subcontratista: ISubcontratista = sampleWithRequiredData;
        const subcontratistaCollection: ISubcontratista[] = [
          {
            ...subcontratista,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSubcontratistaToCollectionIfMissing(subcontratistaCollection, subcontratista);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Subcontratista to an array that doesn't contain it", () => {
        const subcontratista: ISubcontratista = sampleWithRequiredData;
        const subcontratistaCollection: ISubcontratista[] = [sampleWithPartialData];
        expectedResult = service.addSubcontratistaToCollectionIfMissing(subcontratistaCollection, subcontratista);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subcontratista);
      });

      it('should add only unique Subcontratista to an array', () => {
        const subcontratistaArray: ISubcontratista[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const subcontratistaCollection: ISubcontratista[] = [sampleWithRequiredData];
        expectedResult = service.addSubcontratistaToCollectionIfMissing(subcontratistaCollection, ...subcontratistaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subcontratista: ISubcontratista = sampleWithRequiredData;
        const subcontratista2: ISubcontratista = sampleWithPartialData;
        expectedResult = service.addSubcontratistaToCollectionIfMissing([], subcontratista, subcontratista2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subcontratista);
        expect(expectedResult).toContain(subcontratista2);
      });

      it('should accept null and undefined values', () => {
        const subcontratista: ISubcontratista = sampleWithRequiredData;
        expectedResult = service.addSubcontratistaToCollectionIfMissing([], null, subcontratista, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subcontratista);
      });

      it('should return initial array if no Subcontratista is added', () => {
        const subcontratistaCollection: ISubcontratista[] = [sampleWithRequiredData];
        expectedResult = service.addSubcontratistaToCollectionIfMissing(subcontratistaCollection, undefined, null);
        expect(expectedResult).toEqual(subcontratistaCollection);
      });
    });

    describe('compareSubcontratista', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSubcontratista(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSubcontratista(entity1, entity2);
        const compareResult2 = service.compareSubcontratista(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSubcontratista(entity1, entity2);
        const compareResult2 = service.compareSubcontratista(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSubcontratista(entity1, entity2);
        const compareResult2 = service.compareSubcontratista(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
