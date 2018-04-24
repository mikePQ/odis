import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BasicActivityComponent } from './basic-activity.component';

describe('BasicActivityComponent', () => {
  let component: BasicActivityComponent;
  let fixture: ComponentFixture<BasicActivityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BasicActivityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BasicActivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
