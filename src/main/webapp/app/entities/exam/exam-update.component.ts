import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IExam, Exam } from 'app/shared/model/exam.model';
import { ExamService } from './exam.service';
import { IAttendedExam } from 'app/shared/model/attended-exam.model';
import { AttendedExamService } from 'app/entities/attended-exam/attended-exam.service';

@Component({
  selector: 'jhi-exam-update',
  templateUrl: './exam-update.component.html',
})
export class ExamUpdateComponent implements OnInit {
  isSaving = false;
  attendedexams: IAttendedExam[] = [];

  editForm = this.fb.group({
    id: [],
    count: [],
    name: [],
    level: [],
    attendedExamId: [],
  });

  constructor(
    protected examService: ExamService,
    protected attendedExamService: AttendedExamService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ exam }) => {
      this.updateForm(exam);

      this.attendedExamService.query().subscribe((res: HttpResponse<IAttendedExam[]>) => (this.attendedexams = res.body || []));
    });
  }

  updateForm(exam: IExam): void {
    this.editForm.patchValue({
      id: exam.id,
      count: exam.count,
      name: exam.name,
      level: exam.level,
      attendedExamId: exam.attendedExamId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const exam = this.createFromForm();
    if (exam.id !== undefined) {
      this.subscribeToSaveResponse(this.examService.update(exam));
    } else {
      this.subscribeToSaveResponse(this.examService.create(exam));
    }
  }

  private createFromForm(): IExam {
    return {
      ...new Exam(),
      id: this.editForm.get(['id'])!.value,
      count: this.editForm.get(['count'])!.value,
      name: this.editForm.get(['name'])!.value,
      level: this.editForm.get(['level'])!.value,
      attendedExamId: this.editForm.get(['attendedExamId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExam>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IAttendedExam): any {
    return item.id;
  }
}
