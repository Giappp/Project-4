import {
  Directive,
  ElementRef,
  inject,
  input,
  OnInit,
  Renderer2,
} from '@angular/core';

@Directive({
  standalone: true,
  selector: '[active]',
})
export default class ActiveHeader {
  activeHeader = input();
  private readonly renderer = inject(Renderer2);
  private readonly el = inject(ElementRef);

  updateActiveFlag(selectedItem: string): void {
    if (this.activeHeader() == selectedItem) {
      this.renderer.addClass(this.el.nativeElement, '');
    } else {
      this.renderer.removeClass(this.el.nativeElement, 'active');
    }
  }
}
