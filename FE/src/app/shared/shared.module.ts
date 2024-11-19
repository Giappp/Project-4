import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FilterPipe } from './pipe/filter.pipe';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BreadcrumbComponentDemo } from './components/breadcrumb/breadcrumb.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [FilterPipe],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    BreadcrumbComponentDemo,
    FontAwesomeModule,
  ],
  exports: [
    FilterPipe,
    CommonModule,
    FormsModule,
    RouterModule,
    BreadcrumbComponentDemo,
    FontAwesomeModule,
  ],
})
export class SharedModule {}
