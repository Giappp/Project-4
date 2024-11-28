import { Directive, Input } from '@angular/core';
import { AbstractControl, ValidationErrors, NG_VALIDATORS, Validator, ValidatorFn } from '@angular/forms';

export function passwordValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const pass = control.value;

    if (!pass) {
      return null; // Don't validate empty values
    }

    const hasUppercase = /[A-Z]/.test(pass);
    const hasNumber = /[0-9]/.test(pass);
    const hasMinLength = pass.length >= 6; // Minimum length check

    const passwordValid = hasNumber && hasUppercase && hasMinLength;

    return !passwordValid ? { passwordInvalid: true } : null;
  };
}

@Directive({
  selector: '[appPassValidator]',
  providers: [{ provide: NG_VALIDATORS, useExisting: PassValidatorDirective, multi: true }]
})
export class PassValidatorDirective implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    return passwordValidator()(control);
  }

  registerOnValidatorChange(fn: () => void): void {
    this.onValidatorChange = fn;
  }

// Somewhere else in your code (for example, when validation conditions change)
  private onValidatorChange: () => void = () => {};

  triggerValidationChange() {
    if (this.onValidatorChange) {
      this.onValidatorChange(); // Notify Angular that validation has changed
    }
  }

}
