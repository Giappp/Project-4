import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WinterCollectionComponent } from './winter-collection.component';

describe('WinterCollectionComponent', () => {
  let component: WinterCollectionComponent;
  let fixture: ComponentFixture<WinterCollectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [WinterCollectionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WinterCollectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
