import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SpaceSeparatedPipe } from './space-separated.pipe';

@NgModule({
  declarations: [
    SpaceSeparatedPipe,
  ],
  imports: [
    CommonModule
  ],
  exports: [
    SpaceSeparatedPipe
  ]
})
export class PipesModule { }
