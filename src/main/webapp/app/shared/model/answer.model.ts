export interface IAnswer {
  id?: number;
  answer?: string;
  questionId?: number;
}

export class Answer implements IAnswer {
  constructor(public id?: number, public answer?: string, public questionId?: number) {}
}
