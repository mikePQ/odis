import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HostTableElementComponent } from './host-table-element.component';

describe('HostTableElementComponent', () => {
  let component: HostTableElementComponent;
  let fixture: ComponentFixture<HostTableElementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HostTableElementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HostTableElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
