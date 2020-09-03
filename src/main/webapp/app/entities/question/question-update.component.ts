import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IQuestion, Question } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';
import { IAnswer } from 'app/shared/model/answer.model';
import { AnswerService } from 'app/entities/answer/answer.service';

@Component({
  selector: 'jhi-question-update',
  templateUrl: './question-update.component.html',
})
export class QuestionUpdateComponent implements OnInit {
  isSaving = false;
  answers: IAnswer[] = [];

  editForm = this.fb.group({
    id: [],
    question: [],
    questionlevel: [],
    answer: [],
  });

  constructor(
    protected questionService: QuestionService,
    protected answerService: AnswerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ question }) => {
      this.updateForm(question);

      this.answerService
        .query({ filter: 'question-is-null' })
        .pipe(
          map((res: HttpResponse<IAnswer[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAnswer[]) => {
          if (!question.answer || !question.answer.id) {
            this.answers = resBody;
          } else {
            this.answerService
              .find(question.answer.id)
              .pipe(
                map((subRes: HttpResponse<IAnswer>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAnswer[]) => (this.answers = concatRes));
          }
        });
    });
  }

  updateForm(question: IQuestion): void {
    this.editForm.patchValue({
      id: question.id,
      question: question.question,
      questionlevel: question.questionlevel,
      answer: question.answer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const question = this.createFromForm();
    if (question.id !== undefined) {
      this.subscribeToSaveResponse(this.questionService.update(question));
    } else {
      this.subscribeToSaveResponse(this.questionService.create(question));
    }
  }

  private createFromForm(): IQuestion {
    return {
      ...new Question(),
      id: this.editForm.get(['id'])!.value,
      question: this.editForm.get(['question'])!.value,
      questionlevel: this.editForm.get(['questionlevel'])!.value,
      answer: this.editForm.get(['answer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>): void {
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

  trackById(index: number, item: IAnswer): any {
    return item.id;
  }
}
