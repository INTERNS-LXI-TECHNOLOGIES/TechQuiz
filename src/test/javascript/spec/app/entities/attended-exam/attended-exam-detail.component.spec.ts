import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TechQuizTestModule } from '../../../test.module';
import { AttendedExamDetailComponent } from 'app/entities/attended-exam/attended-exam-detail.component';
import { AttendedExam } from 'app/shared/model/attended-exam.model';

describe('Component Tests', () => {
  describe('AttendedExam Management Detail Component', () => {
    let comp: AttendedExamDetailComponent;
    let fixture: ComponentFixture<AttendedExamDetailComponent>;
    const route = ({ data: of({ attendedExam: new AttendedExam(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechQuizTestModule],
        declarations: [AttendedExamDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AttendedExamDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttendedExamDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load attendedExam on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attendedExam).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
