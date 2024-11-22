import { Directive, Input } from '@angular/core';
import { AbstractControl, FormControl, NG_VALIDATORS, ValidationErrors, Validator, ValidatorFn } from '@angular/forms';

export function passwordValidator(): ValidatorFn {
  return (control: AbstractControl) : ValidationErrors | null => {
    const pass = control.value;

    if(!pass) {
      return null;
    }
    const hasUppercase = /[A-Z]+/.test(pass);
    const hasNumber = /[0-9]+/.test(pass);

    const passwordValid = hasNumber && hasUppercase;
    
    return !passwordValid ? {passwordInvalid: true} : null;
  }
}


@Directive({
  selector: '[appPassValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: PassValidatorDirective, multi: true}]
})
export class PassValidatorDirective  implements Validator{
  validate(control: AbstractControl): ValidationErrors | null {
    return passwordValidator()(control);
  }
  registerOnValidatorChange?(fn: () => void): void {
    throw new Error('Method not implemented.');
  }

}
