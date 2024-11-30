import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password-component.component.html',
  styleUrls: ['./forgot-password-component.component.css'] // Fixed to 'styleUrls'
})
export class ForgotPasswordComponent {
  forgotPasswordForm: FormGroup;
  message: string = '';
  error: string = '';

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSubmit() {
    if (this.forgotPasswordForm.valid) {
      const email = this.forgotPasswordForm.value.email;
      this.authService.requestPasswordReset(email).subscribe({
        next: (response: any) => {
          this.message = 'Password reset link sent to your email.';
          this.error = '';
        },
        error: (err: any) => {
          this.error = 'Error sending password reset link. Please try again.';
          this.message = '';
          console.error('Error details:', err); // Log the error for debugging
        }
      });
    }
  }

  // Optional: Navigate back to login
  navigateToLogin() {
    this.router.navigate(['/login']);
  }
}