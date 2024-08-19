import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(value: any[]|string[], args: string): any[] {
    if(!value)
      return [];
    if(!args)
      return [];
    args = this.nomallizeString(args);
    return value.filter(
      val => {return this.nomallizeString(val).includes(args);}
    )
  }

  private nomallizeString(args: string): string {
    return args.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLocaleLowerCase();
  }

}
