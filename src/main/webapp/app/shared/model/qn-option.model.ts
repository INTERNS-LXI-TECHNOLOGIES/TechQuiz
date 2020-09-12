export interface IQnOption {
  id?: number;
  option?: string;
  questionId?: number;
}

export class QnOption implements IQnOption {
  constructor(public id?: number, public option?: string, public questionId?: number) {}
}
