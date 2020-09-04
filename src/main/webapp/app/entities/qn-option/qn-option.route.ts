import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQnOption, QnOption } from 'app/shared/model/qn-option.model';
import { QnOptionService } from './qn-option.service';
import { QnOptionComponent } from './qn-option.component';
import { QnOptionDetailComponent } from './qn-option-detail.component';
import { QnOptionUpdateComponent } from './qn-option-update.component';

@Injectable({ providedIn: 'root' })
export class QnOptionResolve implements Resolve<IQnOption> {
  constructor(private service: QnOptionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQnOption> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((qnOption: HttpResponse<QnOption>) => {
          if (qnOption.body) {
            return of(qnOption.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QnOption());
  }
}

export const qnOptionRoute: Routes = [
  {
    path: '',
    component: QnOptionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'techQuizApp.qnOption.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QnOptionDetailComponent,
    resolve: {
      qnOption: QnOptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'techQuizApp.qnOption.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QnOptionUpdateComponent,
    resolve: {
      qnOption: QnOptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'techQuizApp.qnOption.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QnOptionUpdateComponent,
    resolve: {
      qnOption: QnOptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'techQuizApp.qnOption.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
