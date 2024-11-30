import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'replace'
})
export class ReplacePipe implements PipeTransform {
  transform(value: number, find: number, replace: number): number {

    let valueAsString = value.toString();
    let findAsString = find.toString();
    let replaceAsString = replace.toString();


    let replacedValueAsString = valueAsString.replace(findAsString, replaceAsString);

    let replacedValue = parseFloat(replacedValueAsString);

    return replacedValue;
  }
}