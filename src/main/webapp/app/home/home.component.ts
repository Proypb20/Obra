import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

import { HttpResponse } from '@angular/common/http';
import { ISaldo } from 'app/entities/saldo/saldo.model';
import { SaldoService } from 'app/entities/saldo/service/saldo.service';
import { IAdvPendRep } from 'app/entities/adv-pend-rep/adv-pend-rep.model';
import { AdvPendRepService } from 'app/entities/adv-pend-rep/service/adv-pend-rep.service';
import { ASC, DESC } from 'app/config/navigation.constants';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  saldos: ISaldo[];
  advPendReps: IAdvPendRep[];

  private readonly destroy$ = new Subject<void>();

  constructor(
    private accountService: AccountService,
    private router: Router,
    private saldoService: SaldoService,
    private advPendRepService: AdvPendRepService
  ) {
    this.saldos = [];
    this.advPendReps = [];
  }

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
    this.loadTareas();
    this.loadSaldos();
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadSaldos(): void {
    this.saldoService.query().subscribe((res: HttpResponse<ISaldo[]>) => (this.saldos = res.body!));
  }

  loadTareas(): void {
    const queryObject = {
      sort: this.getSortQueryParam('id', true),
    };
    this.advPendRepService.query(queryObject).subscribe((res: HttpResponse<IAdvPendRep[]>) => (this.advPendReps = res.body!));
  }

  protected getSortQueryParam(predicate = 'id', ascending = true): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }
}
