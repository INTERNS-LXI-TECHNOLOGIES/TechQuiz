import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAttendedExam, AttendedExam } from 'app/shared/model/attended-exam.model';
import { AttendedExamService } from './attended-exam.service';
import { AttendedExamComponent } from './attended-exam.component';
import { AttendedExamDetailComponent } from './attended-exam-detail.component';
import { AttendedExamUpdateComponent } from './attended-exam-update.component';

@Injectable({ providedIn: 'root' })
export class AttendedExamResolve implements Resolve<IAttendedExam> {
  constructor(private service: AttendedExamService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttendedExam> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attendedExam: HttpResponse<AttendedExam>) => {
          if (attendedExam.body) {
            return of(attendedExam.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AttendedExam());
  }
}

export const attendedExamRoute: Routes = [
  {
    path: '',
    component: AttendedExamComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'techQuizApp.attendedExam.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AttendedExamDetailComponent,
    resolve: {
      attendedExam: AttendedExamResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'techQuizApp.attendedExam.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttendedExamUpdateComponent,
    resolve: {
      attendedExam: AttendedExamResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'techQuizApp.attendedExam.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AttendedExamUpdateComponent,
    resolve: {
      attendedExam: AttendedExamResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'techQuizApp.attendedExam.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
