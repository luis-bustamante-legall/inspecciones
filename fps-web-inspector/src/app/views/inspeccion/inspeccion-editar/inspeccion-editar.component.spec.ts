import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InspeccionEditarComponent } from './inspeccion-editar.component';

describe('InspeccionEditarComponent', () => {
  let component: InspeccionEditarComponent;
  let fixture: ComponentFixture<InspeccionEditarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InspeccionEditarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InspeccionEditarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
