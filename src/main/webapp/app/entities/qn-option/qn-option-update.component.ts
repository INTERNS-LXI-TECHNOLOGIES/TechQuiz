import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQnOption, QnOption } from 'app/shared/model/qn-option.model';
import { QnOptionService } from './qn-option.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question/question.service';

@Component({
  selector: 'jhi-qn-option-update',
  templateUrl: './qn-option-update.component.html',
})
export class QnOptionUpdateComponent implements OnInit {
  isSaving = false;
  questions: IQuestion[] = [];

  editForm = this.fb.group({
    id: [],
    option: [],
    question: [],
  });

  constructor(
    protected qnOptionService: QnOptionService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qnOption }) => {
      this.updateForm(qnOption);

      this.questionService.query().subscribe((res: HttpResponse<IQuestion[]>) => (this.questions = res.body || []));
    });
  }

  updateForm(qnOption: IQnOption): void {
    this.editForm.patchValue({
      id: qnOption.id,
      option: qnOption.option,
      question: qnOption.question,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qnOption = this.createFromForm();
    if (qnOption.id !== undefined) {
      this.subscribeToSaveResponse(this.qnOptionService.update(qnOption));
    } else {
      this.subscribeToSaveResponse(this.qnOptionService.create(qnOption));
    }
  }

  private createFromForm(): IQnOption {
    return {
      ...new QnOption(),
      id: this.editForm.get(['id'])!.value,
      option: this.editForm.get(['option'])!.value,
      question: this.editForm.get(['question'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQnOption>>): void {
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

  trackById(index: number, item: IQuestion): any {
    return item.id;
  }
}
