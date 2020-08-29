import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'question',
        loadChildren: () => import('./question/question.module').then(m => m.TechQuizQuestionModule),
      },
      {
        path: 'answer',
        loadChildren: () => import('./answer/answer.module').then(m => m.TechQuizAnswerModule),
      },
      {
        path: 'attended-exam',
        loadChildren: () => import('./attended-exam/attended-exam.module').then(m => m.TechQuizAttendedExamModule),
      },
      {
        path: 'qn-option',
        loadChildren: () => import('./qn-option/qn-option.module').then(m => m.TechQuizQnOptionModule),
      },
      {
        path: 'exam',
        loadChildren: () => import('./exam/exam.module').then(m => m.TechQuizExamModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TechQuizEntityModule {}
