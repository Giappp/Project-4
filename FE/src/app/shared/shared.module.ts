import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FilterPipe } from './pipe/filter.pipe';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [FilterPipe],
  imports: [CommonModule, FormsModule, RouterModule],
  exports: [FilterPipe],
})
export class SharedModule {}
