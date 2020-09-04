import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAttendedExam, AttendedExam } from 'app/shared/model/attended-exam.model';
import { AttendedExamService } from './attended-exam.service';

@Component({
  selector: 'jhi-attended-exam-update',
  templateUrl: './attended-exam-update.component.html',
})
export class AttendedExamUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    total: [],
    result: [],
  });

  constructor(protected attendedExamService: AttendedExamService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendedExam }) => {
      this.updateForm(attendedExam);
    });
  }

  updateForm(attendedExam: IAttendedExam): void {
    this.editForm.patchValue({
      id: attendedExam.id,
      total: attendedExam.total,
      result: attendedExam.result,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attendedExam = this.createFromForm();
    if (attendedExam.id !== undefined) {
      this.subscribeToSaveResponse(this.attendedExamService.update(attendedExam));
    } else {
      this.subscribeToSaveResponse(this.attendedExamService.create(attendedExam));
    }
  }

  private createFromForm(): IAttendedExam {
    return {
      ...new AttendedExam(),
      id: this.editForm.get(['id'])!.value,
      total: this.editForm.get(['total'])!.value,
      result: this.editForm.get(['result'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendedExam>>): void {
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
}
