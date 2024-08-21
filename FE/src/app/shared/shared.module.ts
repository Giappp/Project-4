import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { FilterPipe } from './pipe/filter.pipe';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [HeaderComponent, FooterComponent, FilterPipe],
  imports: [CommonModule, FormsModule, RouterModule],
  exports: [HeaderComponent, FooterComponent, FilterPipe],
})
export class SharedModule {}
