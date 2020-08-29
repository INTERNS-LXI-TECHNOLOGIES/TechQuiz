import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttendedExam } from 'app/shared/model/attended-exam.model';
import { AttendedExamService } from './attended-exam.service';
import { AttendedExamDeleteDialogComponent } from './attended-exam-delete-dialog.component';

@Component({
  selector: 'jhi-attended-exam',
  templateUrl: './attended-exam.component.html',
})
export class AttendedExamComponent implements OnInit, OnDestroy {
  attendedExams?: IAttendedExam[];
  eventSubscriber?: Subscription;

  constructor(
    protected attendedExamService: AttendedExamService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.attendedExamService.query().subscribe((res: HttpResponse<IAttendedExam[]>) => (this.attendedExams = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAttendedExams();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttendedExam): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAttendedExams(): void {
    this.eventSubscriber = this.eventManager.subscribe('attendedExamListModification', () => this.loadAll());
  }

  delete(attendedExam: IAttendedExam): void {
    const modalRef = this.modalService.open(AttendedExamDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.attendedExam = attendedExam;
  }
}
