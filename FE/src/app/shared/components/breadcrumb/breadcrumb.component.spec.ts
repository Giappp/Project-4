import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BreadcrumbComponentDemo } from './breadcrumb.component';

describe('BreadcrumbComponent', () => {
  let component: BreadcrumbComponentDemo;
  let fixture: ComponentFixture<BreadcrumbComponentDemo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BreadcrumbComponentDemo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BreadcrumbComponentDemo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
