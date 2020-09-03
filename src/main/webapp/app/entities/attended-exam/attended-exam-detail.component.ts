import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttendedExam } from 'app/shared/model/attended-exam.model';

@Component({
  selector: 'jhi-attended-exam-detail',
  templateUrl: './attended-exam-detail.component.html',
})
export class AttendedExamDetailComponent implements OnInit {
  attendedExam: IAttendedExam | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendedExam }) => (this.attendedExam = attendedExam));
  }

  previousState(): void {
    window.history.back();
  }
}
