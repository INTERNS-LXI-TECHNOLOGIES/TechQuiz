import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TechQuizTestModule } from '../../../test.module';
import { QnOptionUpdateComponent } from 'app/entities/qn-option/qn-option-update.component';
import { QnOptionService } from 'app/entities/qn-option/qn-option.service';
import { QnOption } from 'app/shared/model/qn-option.model';

describe('Component Tests', () => {
  describe('QnOption Management Update Component', () => {
    let comp: QnOptionUpdateComponent;
    let fixture: ComponentFixture<QnOptionUpdateComponent>;
    let service: QnOptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechQuizTestModule],
        declarations: [QnOptionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QnOptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QnOptionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QnOptionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QnOption(123);
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
        const entity = new QnOption();
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
