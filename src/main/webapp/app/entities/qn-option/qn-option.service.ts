import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQnOption } from 'app/shared/model/qn-option.model';

type EntityResponseType = HttpResponse<IQnOption>;
type EntityArrayResponseType = HttpResponse<IQnOption[]>;

@Injectable({ providedIn: 'root' })
export class QnOptionService {
  public resourceUrl = SERVER_API_URL + 'api/qn-options';

  constructor(protected http: HttpClient) {}

  create(qnOption: IQnOption): Observable<EntityResponseType> {
    return this.http.post<IQnOption>(this.resourceUrl, qnOption, { observe: 'response' });
  }

  update(qnOption: IQnOption): Observable<EntityResponseType> {
    return this.http.put<IQnOption>(this.resourceUrl, qnOption, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQnOption>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQnOption[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
