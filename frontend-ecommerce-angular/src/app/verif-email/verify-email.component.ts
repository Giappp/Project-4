import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../interfaces/User';
import { AuthService } from '../services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-verif-email',
  templateUrl: './verify-email.component.html',
  styleUrls: ['./verify-email.component.css']
})
export class VerifyEmailComponent implements OnInit {
  verifyForm!: FormGroup;
  user: User = new User();
  err: string = "";

  constructor(
      private formBuilder: FormBuilder,
      private route: ActivatedRoute,
      private authService: AuthService,
      private router: Router,
      private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.verifyForm = this.formBuilder.group({
      code: ['', Validators.required]
    });
    this.user = this.authService.getRegisteredUser(); // Use the getter method
  }

  onValidateEmail(): void {
    if (this.verifyForm.invalid) {
      this.err = "The code is required."; // Translated error message
      return;
    }

    const code = this.verifyForm.get('code')?.value;

    this.authService.validateEmail(code).subscribe({
      next: (res) => {
        this.toastr.success('Successful login!', 'Success');

        this.authService.login(this.user).subscribe({
          next: (data) => {
            // Check if data is not null and contains headers
            const jwToken = data?.headers?.get('Authorization');
            if (jwToken) {
              this.authService.saveToken(jwToken);
              this.router.navigate(['/']);
            } else {
              this.err = "No token received.";
            }
          },
          error: (err: any) => {
            console.error(err);
            this.err = "Login failed. Please try again.";
          }
        });
      },
      error: (err: any) => {
        if (err.error?.errorCode === "INVALID_TOKEN") {
          this.err = "Your code is invalid!";
        } else if (err.error?.errorCode === "EXPIRED_TOKEN") {
          this.err = "Your code has expired!";
        } else {
          this.err = "An unexpected error occurred. Please try again.";
        }
      }
    });
  }
}