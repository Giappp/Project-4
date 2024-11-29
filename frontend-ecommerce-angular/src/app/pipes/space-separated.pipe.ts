import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'spaceSeparated'
})
export class SpaceSeparatedPipe implements PipeTransform {
  transform(value: number | string): string {
    const stringValue = typeof value === 'number' ? value.toString() : value;
    return stringValue.replace(/\B(?=(\d{3})+(?!\d))/g, ' ');
  }
}
