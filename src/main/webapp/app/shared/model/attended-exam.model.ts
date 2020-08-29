import { IExam } from 'app/shared/model/exam.model';

export interface IAttendedExam {
  id?: number;
  total?: number;
  result?: boolean;
  exams?: IExam[];
}

export class AttendedExam implements IAttendedExam {
  constructor(public id?: number, public total?: number, public result?: boolean, public exams?: IExam[]) {
    this.result = this.result || false;
  }
}
