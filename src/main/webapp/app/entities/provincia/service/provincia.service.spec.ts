import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProvincia } from '../provincia.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../provincia.test-samples';

import { ProvinciaService } from './provincia.service';

const requireRestSample: IProvincia = {
  ...sampleWithRequiredData,
};

describe('Provincia Service', () => {
  let service: ProvinciaService;
  let httpMock: HttpTestingController;
  let expectedResult: IProvincia | IProvincia[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProvinciaService);
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

    it('should create a Provincia', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const provincia = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(provincia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Provincia', () => {
      const provincia = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(provincia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Provincia', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Provincia', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Provincia', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProvinciaToCollectionIfMissing', () => {
      it('should add a Provincia to an empty array', () => {
        const provincia: IProvincia = sampleWithRequiredData;
        expectedResult = service.addProvinciaToCollectionIfMissing([], provincia);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(provincia);
      });

      it('should not add a Provincia to an array that contains it', () => {
        const provincia: IProvincia = sampleWithRequiredData;
        const provinciaCollection: IProvincia[] = [
          {
            ...provincia,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProvinciaToCollectionIfMissing(provinciaCollection, provincia);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Provincia to an array that doesn't contain it", () => {
        const provincia: IProvincia = sampleWithRequiredData;
        const provinciaCollection: IProvincia[] = [sampleWithPartialData];
        expectedResult = service.addProvinciaToCollectionIfMissing(provinciaCollection, provincia);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(provincia);
      });

      it('should add only unique Provincia to an array', () => {
        const provinciaArray: IProvincia[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const provinciaCollection: IProvincia[] = [sampleWithRequiredData];
        expectedResult = service.addProvinciaToCollectionIfMissing(provinciaCollection, ...provinciaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const provincia: IProvincia = sampleWithRequiredData;
        const provincia2: IProvincia = sampleWithPartialData;
        expectedResult = service.addProvinciaToCollectionIfMissing([], provincia, provincia2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(provincia);
        expect(expectedResult).toContain(provincia2);
      });

      it('should accept null and undefined values', () => {
        const provincia: IProvincia = sampleWithRequiredData;
        expectedResult = service.addProvinciaToCollectionIfMissing([], null, provincia, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(provincia);
      });

      it('should return initial array if no Provincia is added', () => {
        const provinciaCollection: IProvincia[] = [sampleWithRequiredData];
        expectedResult = service.addProvinciaToCollectionIfMissing(provinciaCollection, undefined, null);
        expect(expectedResult).toEqual(provinciaCollection);
      });
    });

    describe('compareProvincia', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProvincia(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProvincia(entity1, entity2);
        const compareResult2 = service.compareProvincia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProvincia(entity1, entity2);
        const compareResult2 = service.compareProvincia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProvincia(entity1, entity2);
        const compareResult2 = service.compareProvincia(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
