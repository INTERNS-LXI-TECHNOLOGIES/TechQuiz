import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendedExam } from 'app/shared/model/attended-exam.model';
import { AttendedExamService } from './attended-exam.service';

@Component({
  templateUrl: './attended-exam-delete-dialog.component.html',
})
export class AttendedExamDeleteDialogComponent {
  attendedExam?: IAttendedExam;

  constructor(
    protected attendedExamService: AttendedExamService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attendedExamService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attendedExamListModification');
      this.activeModal.close();
    });
  }
}
