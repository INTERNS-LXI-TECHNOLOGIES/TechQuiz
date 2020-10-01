import { IQnOption } from 'app/shared/model/qn-option.model';
import { QuestionLevel } from 'app/shared/model/enumerations/question-level.model';

export interface IQuestion {
  id?: number;
  question?: string;
  questionlevel?: QuestionLevel;
  answerId?: number;
  qnOptions?: IQnOption[];
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public question?: string,
    public questionlevel?: QuestionLevel,
    public answerId?: number,
    public qnOptions?: IQnOption[]
  ) {}
}
