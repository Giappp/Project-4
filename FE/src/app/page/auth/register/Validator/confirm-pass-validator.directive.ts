import { Directive } from '@angular/core';
import {
  AbstractControl,
  NG_VALIDATORS,
  ValidationErrors,
  Validator,
  ValidatorFn,
} from '@angular/forms';

export const confirmPassValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const password = control.get('password');
  const confirmPassword = control.get('confirmPassword');

  // Check if both controls exist and compare their values
  if (password && confirmPassword && password.value !== confirmPassword.value) {
    return { passwordsMismatch: true }; // Return error if passwords do not match
  }

  return null; // Return null if validation passes
};

@Directive({
  selector: '[appConfirmPassValidator]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useExisting: ConfirmPassValidatorDirective,
      multi: true,
    },
  ],
})
export class ConfirmPassValidatorDirective implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    return confirmPassValidator(control); // Use the validator function
  }

  registerOnValidatorChange?(fn: () => void): void {
    // This can be implemented if you need to handle changes dynamically
  }
}
