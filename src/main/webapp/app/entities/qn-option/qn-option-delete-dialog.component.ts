import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQnOption } from 'app/shared/model/qn-option.model';
import { QnOptionService } from './qn-option.service';

@Component({
  templateUrl: './qn-option-delete-dialog.component.html',
})
export class QnOptionDeleteDialogComponent {
  qnOption?: IQnOption;

  constructor(protected qnOptionService: QnOptionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qnOptionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('qnOptionListModification');
      this.activeModal.close();
    });
  }
}
