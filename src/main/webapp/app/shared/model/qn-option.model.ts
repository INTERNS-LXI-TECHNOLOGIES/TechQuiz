import { IQuestion } from 'app/shared/model/question.model';

export interface IQnOption {
  id?: number;
  option?: string;
  question?: IQuestion;
}

export class QnOption implements IQnOption {
  constructor(public id?: number, public option?: string, public question?: IQuestion) {}
}
