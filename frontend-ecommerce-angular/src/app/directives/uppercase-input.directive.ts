import { Directive, HostListener } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
  standalone: true,
  selector: '[appUppercaseInput]'
})
export class UppercaseInputDirective {
  constructor(private control: NgControl) {}

  // Directive to convert input letters to uppercase
  @HostListener('input', ['$event'])
  onInputChange(event: Event): void {
    const input = event.target as HTMLInputElement; // Type assertion for better type safety
    if (this.control && this.control.control) {
      this.control.control.setValue(input.value.toUpperCase());
    }
  }
}