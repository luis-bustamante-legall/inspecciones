import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InspeccionVerComponent } from './inspeccion-ver.component';

describe('InspeccionVerComponent', () => {
  let component: InspeccionVerComponent;
  let fixture: ComponentFixture<InspeccionVerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InspeccionVerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InspeccionVerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
