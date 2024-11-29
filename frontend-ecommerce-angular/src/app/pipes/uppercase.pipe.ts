import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'uppercase'
})
export class UppercasePipe implements PipeTransform {

  transform(value: string): string {
    const uppercase = value.charAt(0).toUpperCase();
    const subStr = value.substring(1);
    return uppercase + subStr;
  }


}
