import { IAttendedExam } from 'app/shared/model/attended-exam.model';
import { IQuestion } from 'app/shared/model/question.model';
import { ExamLevel } from 'app/shared/model/enumerations/exam-level.model';

export interface IExam {
  id?: number;
  count?: number;
  name?: string;
  level?: ExamLevel;
  attendedExam?: IAttendedExam;
  questions?: IQuestion[];
}

export class Exam implements IExam {
  constructor(
    public id?: number,
    public count?: number,
    public name?: string,
    public level?: ExamLevel,
    public attendedExam?: IAttendedExam,
    public questions?: IQuestion[]
  ) {}
}
