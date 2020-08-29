import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQnOption } from 'app/shared/model/qn-option.model';
import { QnOptionService } from './qn-option.service';
import { QnOptionDeleteDialogComponent } from './qn-option-delete-dialog.component';

@Component({
  selector: 'jhi-qn-option',
  templateUrl: './qn-option.component.html',
})
export class QnOptionComponent implements OnInit, OnDestroy {
  qnOptions?: IQnOption[];
  eventSubscriber?: Subscription;

  constructor(protected qnOptionService: QnOptionService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.qnOptionService.query().subscribe((res: HttpResponse<IQnOption[]>) => (this.qnOptions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQnOptions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQnOption): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQnOptions(): void {
    this.eventSubscriber = this.eventManager.subscribe('qnOptionListModification', () => this.loadAll());
  }

  delete(qnOption: IQnOption): void {
    const modalRef = this.modalService.open(QnOptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.qnOption = qnOption;
  }
}
