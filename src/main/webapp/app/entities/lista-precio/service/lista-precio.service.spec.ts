import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IListaPrecio } from '../lista-precio.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../lista-precio.test-samples';

import { ListaPrecioService, RestListaPrecio } from './lista-precio.service';

const requireRestSample: RestListaPrecio = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
};

describe('ListaPrecio Service', () => {
  let service: ListaPrecioService;
  let httpMock: HttpTestingController;
  let expectedResult: IListaPrecio | IListaPrecio[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ListaPrecioService);
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

    it('should create a ListaPrecio', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const listaPrecio = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(listaPrecio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ListaPrecio', () => {
      const listaPrecio = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(listaPrecio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ListaPrecio', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ListaPrecio', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ListaPrecio', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addListaPrecioToCollectionIfMissing', () => {
      it('should add a ListaPrecio to an empty array', () => {
        const listaPrecio: IListaPrecio = sampleWithRequiredData;
        expectedResult = service.addListaPrecioToCollectionIfMissing([], listaPrecio);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(listaPrecio);
      });

      it('should not add a ListaPrecio to an array that contains it', () => {
        const listaPrecio: IListaPrecio = sampleWithRequiredData;
        const listaPrecioCollection: IListaPrecio[] = [
          {
            ...listaPrecio,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addListaPrecioToCollectionIfMissing(listaPrecioCollection, listaPrecio);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ListaPrecio to an array that doesn't contain it", () => {
        const listaPrecio: IListaPrecio = sampleWithRequiredData;
        const listaPrecioCollection: IListaPrecio[] = [sampleWithPartialData];
        expectedResult = service.addListaPrecioToCollectionIfMissing(listaPrecioCollection, listaPrecio);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(listaPrecio);
      });

      it('should add only unique ListaPrecio to an array', () => {
        const listaPrecioArray: IListaPrecio[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const listaPrecioCollection: IListaPrecio[] = [sampleWithRequiredData];
        expectedResult = service.addListaPrecioToCollectionIfMissing(listaPrecioCollection, ...listaPrecioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const listaPrecio: IListaPrecio = sampleWithRequiredData;
        const listaPrecio2: IListaPrecio = sampleWithPartialData;
        expectedResult = service.addListaPrecioToCollectionIfMissing([], listaPrecio, listaPrecio2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(listaPrecio);
        expect(expectedResult).toContain(listaPrecio2);
      });

      it('should accept null and undefined values', () => {
        const listaPrecio: IListaPrecio = sampleWithRequiredData;
        expectedResult = service.addListaPrecioToCollectionIfMissing([], null, listaPrecio, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(listaPrecio);
      });

      it('should return initial array if no ListaPrecio is added', () => {
        const listaPrecioCollection: IListaPrecio[] = [sampleWithRequiredData];
        expectedResult = service.addListaPrecioToCollectionIfMissing(listaPrecioCollection, undefined, null);
        expect(expectedResult).toEqual(listaPrecioCollection);
      });
    });

    describe('compareListaPrecio', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareListaPrecio(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareListaPrecio(entity1, entity2);
        const compareResult2 = service.compareListaPrecio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareListaPrecio(entity1, entity2);
        const compareResult2 = service.compareListaPrecio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareListaPrecio(entity1, entity2);
        const compareResult2 = service.compareListaPrecio(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
