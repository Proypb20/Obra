import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUnidadMedida } from '../unidad-medida.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../unidad-medida.test-samples';

import { UnidadMedidaService } from './unidad-medida.service';

const requireRestSample: IUnidadMedida = {
  ...sampleWithRequiredData,
};

describe('UnidadMedida Service', () => {
  let service: UnidadMedidaService;
  let httpMock: HttpTestingController;
  let expectedResult: IUnidadMedida | IUnidadMedida[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UnidadMedidaService);
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

    it('should create a UnidadMedida', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const unidadMedida = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(unidadMedida).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UnidadMedida', () => {
      const unidadMedida = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(unidadMedida).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UnidadMedida', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UnidadMedida', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UnidadMedida', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUnidadMedidaToCollectionIfMissing', () => {
      it('should add a UnidadMedida to an empty array', () => {
        const unidadMedida: IUnidadMedida = sampleWithRequiredData;
        expectedResult = service.addUnidadMedidaToCollectionIfMissing([], unidadMedida);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(unidadMedida);
      });

      it('should not add a UnidadMedida to an array that contains it', () => {
        const unidadMedida: IUnidadMedida = sampleWithRequiredData;
        const unidadMedidaCollection: IUnidadMedida[] = [
          {
            ...unidadMedida,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUnidadMedidaToCollectionIfMissing(unidadMedidaCollection, unidadMedida);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UnidadMedida to an array that doesn't contain it", () => {
        const unidadMedida: IUnidadMedida = sampleWithRequiredData;
        const unidadMedidaCollection: IUnidadMedida[] = [sampleWithPartialData];
        expectedResult = service.addUnidadMedidaToCollectionIfMissing(unidadMedidaCollection, unidadMedida);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(unidadMedida);
      });

      it('should add only unique UnidadMedida to an array', () => {
        const unidadMedidaArray: IUnidadMedida[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const unidadMedidaCollection: IUnidadMedida[] = [sampleWithRequiredData];
        expectedResult = service.addUnidadMedidaToCollectionIfMissing(unidadMedidaCollection, ...unidadMedidaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const unidadMedida: IUnidadMedida = sampleWithRequiredData;
        const unidadMedida2: IUnidadMedida = sampleWithPartialData;
        expectedResult = service.addUnidadMedidaToCollectionIfMissing([], unidadMedida, unidadMedida2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(unidadMedida);
        expect(expectedResult).toContain(unidadMedida2);
      });

      it('should accept null and undefined values', () => {
        const unidadMedida: IUnidadMedida = sampleWithRequiredData;
        expectedResult = service.addUnidadMedidaToCollectionIfMissing([], null, unidadMedida, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(unidadMedida);
      });

      it('should return initial array if no UnidadMedida is added', () => {
        const unidadMedidaCollection: IUnidadMedida[] = [sampleWithRequiredData];
        expectedResult = service.addUnidadMedidaToCollectionIfMissing(unidadMedidaCollection, undefined, null);
        expect(expectedResult).toEqual(unidadMedidaCollection);
      });
    });

    describe('compareUnidadMedida', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUnidadMedida(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUnidadMedida(entity1, entity2);
        const compareResult2 = service.compareUnidadMedida(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUnidadMedida(entity1, entity2);
        const compareResult2 = service.compareUnidadMedida(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUnidadMedida(entity1, entity2);
        const compareResult2 = service.compareUnidadMedida(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
