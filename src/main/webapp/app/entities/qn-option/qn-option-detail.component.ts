import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQnOption } from 'app/shared/model/qn-option.model';

@Component({
  selector: 'jhi-qn-option-detail',
  templateUrl: './qn-option-detail.component.html',
})
export class QnOptionDetailComponent implements OnInit {
  qnOption: IQnOption | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qnOption }) => (this.qnOption = qnOption));
  }

  previousState(): void {
    window.history.back();
  }
}
