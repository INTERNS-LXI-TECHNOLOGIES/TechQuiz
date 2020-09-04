import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechQuizSharedModule } from 'app/shared/shared.module';
import { AttendedExamComponent } from './attended-exam.component';
import { AttendedExamDetailComponent } from './attended-exam-detail.component';
import { AttendedExamUpdateComponent } from './attended-exam-update.component';
import { AttendedExamDeleteDialogComponent } from './attended-exam-delete-dialog.component';
import { attendedExamRoute } from './attended-exam.route';

@NgModule({
  imports: [TechQuizSharedModule, RouterModule.forChild(attendedExamRoute)],
  declarations: [AttendedExamComponent, AttendedExamDetailComponent, AttendedExamUpdateComponent, AttendedExamDeleteDialogComponent],
  entryComponents: [AttendedExamDeleteDialogComponent],
})
export class TechQuizAttendedExamModule {}
