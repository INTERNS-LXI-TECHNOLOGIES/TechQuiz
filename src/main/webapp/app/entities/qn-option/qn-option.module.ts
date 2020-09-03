import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechQuizSharedModule } from 'app/shared/shared.module';
import { QnOptionComponent } from './qn-option.component';
import { QnOptionDetailComponent } from './qn-option-detail.component';
import { QnOptionUpdateComponent } from './qn-option-update.component';
import { QnOptionDeleteDialogComponent } from './qn-option-delete-dialog.component';
import { qnOptionRoute } from './qn-option.route';

@NgModule({
  imports: [TechQuizSharedModule, RouterModule.forChild(qnOptionRoute)],
  declarations: [QnOptionComponent, QnOptionDetailComponent, QnOptionUpdateComponent, QnOptionDeleteDialogComponent],
  entryComponents: [QnOptionDeleteDialogComponent],
})
export class TechQuizQnOptionModule {}
