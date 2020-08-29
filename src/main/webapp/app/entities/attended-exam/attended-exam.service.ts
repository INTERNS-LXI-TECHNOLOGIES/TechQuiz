import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttendedExam } from 'app/shared/model/attended-exam.model';

type EntityResponseType = HttpResponse<IAttendedExam>;
type EntityArrayResponseType = HttpResponse<IAttendedExam[]>;

@Injectable({ providedIn: 'root' })
export class AttendedExamService {
  public resourceUrl = SERVER_API_URL + 'api/attended-exams';

  constructor(protected http: HttpClient) {}

  create(attendedExam: IAttendedExam): Observable<EntityResponseType> {
    return this.http.post<IAttendedExam>(this.resourceUrl, attendedExam, { observe: 'response' });
  }

  update(attendedExam: IAttendedExam): Observable<EntityResponseType> {
    return this.http.put<IAttendedExam>(this.resourceUrl, attendedExam, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttendedExam>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttendedExam[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
