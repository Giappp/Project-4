import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccessoryCollectionComponent } from './accessory-collection.component';

describe('AccessoryCollectionComponent', () => {
  let component: AccessoryCollectionComponent;
  let fixture: ComponentFixture<AccessoryCollectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccessoryCollectionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccessoryCollectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
