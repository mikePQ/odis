import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DateInputModalComponent } from './date-input-modal.component';

describe('DateInputModalComponent', () => {
  let component: DateInputModalComponent;
  let fixture: ComponentFixture<DateInputModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DateInputModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DateInputModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
