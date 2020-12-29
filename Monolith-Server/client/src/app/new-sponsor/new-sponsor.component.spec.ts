import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewSponsorComponent } from './new-sponsor.component';

describe('NewSponsorComponent', () => {
  let component: NewSponsorComponent;
  let fixture: ComponentFixture<NewSponsorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewSponsorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewSponsorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
