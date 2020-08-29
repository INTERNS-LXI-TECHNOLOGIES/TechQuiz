import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TechQuizTestModule } from '../../../test.module';
import { QnOptionComponent } from 'app/entities/qn-option/qn-option.component';
import { QnOptionService } from 'app/entities/qn-option/qn-option.service';
import { QnOption } from 'app/shared/model/qn-option.model';

describe('Component Tests', () => {
  describe('QnOption Management Component', () => {
    let comp: QnOptionComponent;
    let fixture: ComponentFixture<QnOptionComponent>;
    let service: QnOptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechQuizTestModule],
        declarations: [QnOptionComponent],
      })
        .overrideTemplate(QnOptionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QnOptionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QnOptionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new QnOption(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.qnOptions && comp.qnOptions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
