import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMovimiento } from '../movimiento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../movimiento.test-samples';

import { MovimientoService, RestMovimiento } from './movimiento.service';

const requireRestSample: RestMovimiento = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.toJSON(),
};

describe('Movimiento Service', () => {
  let service: MovimientoService;
  let httpMock: HttpTestingController;
  let expectedResult: IMovimiento | IMovimiento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MovimientoService);
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

    it('should create a Movimiento', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const movimiento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(movimiento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Movimiento', () => {
      const movimiento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(movimiento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Movimiento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Movimiento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Movimiento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMovimientoToCollectionIfMissing', () => {
      it('should add a Movimiento to an empty array', () => {
        const movimiento: IMovimiento = sampleWithRequiredData;
        expectedResult = service.addMovimientoToCollectionIfMissing([], movimiento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(movimiento);
      });

      it('should not add a Movimiento to an array that contains it', () => {
        const movimiento: IMovimiento = sampleWithRequiredData;
        const movimientoCollection: IMovimiento[] = [
          {
            ...movimiento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMovimientoToCollectionIfMissing(movimientoCollection, movimiento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Movimiento to an array that doesn't contain it", () => {
        const movimiento: IMovimiento = sampleWithRequiredData;
        const movimientoCollection: IMovimiento[] = [sampleWithPartialData];
        expectedResult = service.addMovimientoToCollectionIfMissing(movimientoCollection, movimiento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(movimiento);
      });

      it('should add only unique Movimiento to an array', () => {
        const movimientoArray: IMovimiento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const movimientoCollection: IMovimiento[] = [sampleWithRequiredData];
        expectedResult = service.addMovimientoToCollectionIfMissing(movimientoCollection, ...movimientoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const movimiento: IMovimiento = sampleWithRequiredData;
        const movimiento2: IMovimiento = sampleWithPartialData;
        expectedResult = service.addMovimientoToCollectionIfMissing([], movimiento, movimiento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(movimiento);
        expect(expectedResult).toContain(movimiento2);
      });

      it('should accept null and undefined values', () => {
        const movimiento: IMovimiento = sampleWithRequiredData;
        expectedResult = service.addMovimientoToCollectionIfMissing([], null, movimiento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(movimiento);
      });

      it('should return initial array if no Movimiento is added', () => {
        const movimientoCollection: IMovimiento[] = [sampleWithRequiredData];
        expectedResult = service.addMovimientoToCollectionIfMissing(movimientoCollection, undefined, null);
        expect(expectedResult).toEqual(movimientoCollection);
      });
    });

    describe('compareMovimiento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMovimiento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMovimiento(entity1, entity2);
        const compareResult2 = service.compareMovimiento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMovimiento(entity1, entity2);
        const compareResult2 = service.compareMovimiento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMovimiento(entity1, entity2);
        const compareResult2 = service.compareMovimiento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
