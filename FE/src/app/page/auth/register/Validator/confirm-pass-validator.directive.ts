import { Directive } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator, ValidatorFn } from '@angular/forms';



export const confirmPassValidator: ValidatorFn = (
  control: AbstractControl
): ValidationErrors | null => {
  const password = control.get('password');
  const confirmPass = control.get('confirmPassword');

  return password && confirmPass && password.value === confirmPass.value ? {passMisMatch: true} : null;
}

@Directive({
  selector: '[appConfirmPassValidator]',
  providers: [
    {provide: NG_VALIDATORS, useExisting: ConfirmPassValidatorDirective, multi: true}
  ]
})

export class ConfirmPassValidatorDirective implements Validator {

  validate(control: AbstractControl): ValidationErrors | null {
    return confirmPassValidator(control);
  }
  registerOnValidatorChange?(fn: () => void): void {
    throw new Error('Method not implemented.');
  }

}
