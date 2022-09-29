import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITarea } from '../tarea.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tarea.test-samples';

import { TareaService } from './tarea.service';

const requireRestSample: ITarea = {
  ...sampleWithRequiredData,
};

describe('Tarea Service', () => {
  let service: TareaService;
  let httpMock: HttpTestingController;
  let expectedResult: ITarea | ITarea[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TareaService);
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

    it('should create a Tarea', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const tarea = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tarea).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Tarea', () => {
      const tarea = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tarea).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Tarea', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Tarea', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Tarea', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTareaToCollectionIfMissing', () => {
      it('should add a Tarea to an empty array', () => {
        const tarea: ITarea = sampleWithRequiredData;
        expectedResult = service.addTareaToCollectionIfMissing([], tarea);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarea);
      });

      it('should not add a Tarea to an array that contains it', () => {
        const tarea: ITarea = sampleWithRequiredData;
        const tareaCollection: ITarea[] = [
          {
            ...tarea,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTareaToCollectionIfMissing(tareaCollection, tarea);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Tarea to an array that doesn't contain it", () => {
        const tarea: ITarea = sampleWithRequiredData;
        const tareaCollection: ITarea[] = [sampleWithPartialData];
        expectedResult = service.addTareaToCollectionIfMissing(tareaCollection, tarea);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarea);
      });

      it('should add only unique Tarea to an array', () => {
        const tareaArray: ITarea[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tareaCollection: ITarea[] = [sampleWithRequiredData];
        expectedResult = service.addTareaToCollectionIfMissing(tareaCollection, ...tareaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tarea: ITarea = sampleWithRequiredData;
        const tarea2: ITarea = sampleWithPartialData;
        expectedResult = service.addTareaToCollectionIfMissing([], tarea, tarea2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarea);
        expect(expectedResult).toContain(tarea2);
      });

      it('should accept null and undefined values', () => {
        const tarea: ITarea = sampleWithRequiredData;
        expectedResult = service.addTareaToCollectionIfMissing([], null, tarea, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarea);
      });

      it('should return initial array if no Tarea is added', () => {
        const tareaCollection: ITarea[] = [sampleWithRequiredData];
        expectedResult = service.addTareaToCollectionIfMissing(tareaCollection, undefined, null);
        expect(expectedResult).toEqual(tareaCollection);
      });
    });

    describe('compareTarea', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTarea(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTarea(entity1, entity2);
        const compareResult2 = service.compareTarea(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTarea(entity1, entity2);
        const compareResult2 = service.compareTarea(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTarea(entity1, entity2);
        const compareResult2 = service.compareTarea(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
