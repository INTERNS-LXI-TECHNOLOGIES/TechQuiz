import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TechQuizTestModule } from '../../../test.module';
import { AttendedExamComponent } from 'app/entities/attended-exam/attended-exam.component';
import { AttendedExamService } from 'app/entities/attended-exam/attended-exam.service';
import { AttendedExam } from 'app/shared/model/attended-exam.model';

describe('Component Tests', () => {
  describe('AttendedExam Management Component', () => {
    let comp: AttendedExamComponent;
    let fixture: ComponentFixture<AttendedExamComponent>;
    let service: AttendedExamService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechQuizTestModule],
        declarations: [AttendedExamComponent],
      })
        .overrideTemplate(AttendedExamComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendedExamComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendedExamService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AttendedExam(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.attendedExams && comp.attendedExams[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
