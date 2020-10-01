import { ExamLevel } from 'app/shared/model/enumerations/exam-level.model';

export interface IExam {
  id?: number;
  count?: number;
  name?: string;
  level?: ExamLevel;
  attendedExamId?: number;
}

export class Exam implements IExam {
  constructor(public id?: number, public count?: number, public name?: string, public level?: ExamLevel, public attendedExamId?: number) {}
}
