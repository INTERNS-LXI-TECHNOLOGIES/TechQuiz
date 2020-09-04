import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TechQuizTestModule } from '../../../test.module';
import { AttendedExamUpdateComponent } from 'app/entities/attended-exam/attended-exam-update.component';
import { AttendedExamService } from 'app/entities/attended-exam/attended-exam.service';
import { AttendedExam } from 'app/shared/model/attended-exam.model';

describe('Component Tests', () => {
  describe('AttendedExam Management Update Component', () => {
    let comp: AttendedExamUpdateComponent;
    let fixture: ComponentFixture<AttendedExamUpdateComponent>;
    let service: AttendedExamService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechQuizTestModule],
        declarations: [AttendedExamUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AttendedExamUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendedExamUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendedExamService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttendedExam(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttendedExam();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
