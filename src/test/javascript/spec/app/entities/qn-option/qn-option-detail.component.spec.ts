import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TechQuizTestModule } from '../../../test.module';
import { QnOptionDetailComponent } from 'app/entities/qn-option/qn-option-detail.component';
import { QnOption } from 'app/shared/model/qn-option.model';

describe('Component Tests', () => {
  describe('QnOption Management Detail Component', () => {
    let comp: QnOptionDetailComponent;
    let fixture: ComponentFixture<QnOptionDetailComponent>;
    const route = ({ data: of({ qnOption: new QnOption(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechQuizTestModule],
        declarations: [QnOptionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QnOptionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QnOptionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load qnOption on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.qnOption).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
