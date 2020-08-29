import { IAnswer } from 'app/shared/model/answer.model';
import { IQnOption } from 'app/shared/model/qn-option.model';
import { IExam } from 'app/shared/model/exam.model';
import { QuestionLevel } from 'app/shared/model/enumerations/question-level.model';

export interface IQuestion {
  id?: number;
  question?: string;
  questionlevel?: QuestionLevel;
  answer?: IAnswer;
  qnOptions?: IQnOption[];
  exams?: IExam[];
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public question?: string,
    public questionlevel?: QuestionLevel,
    public answer?: IAnswer,
    public qnOptions?: IQnOption[],
    public exams?: IExam[]
  ) {}
}
