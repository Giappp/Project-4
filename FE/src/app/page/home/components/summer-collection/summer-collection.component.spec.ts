import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SummerCollectionComponent } from './summer-collection.component';

describe('SummerCollectionComponent', () => {
  let component: SummerCollectionComponent;
  let fixture: ComponentFixture<SummerCollectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SummerCollectionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SummerCollectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
