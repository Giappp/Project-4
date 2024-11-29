import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../interfaces/User';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
    newUserForm!: FormGroup;
    user: User = new User(); // Ensure this User instance has default values as necessary
    errorMessage: string = ''; // String for error messages
    err: number = 0; // Error state (consider renaming for clarity)
    message: string = "wrong pass"; // Error message
    constructor(
        private formBuilder: FormBuilder,
        private authService: AuthService,
        private router: Router,
        private toastr: ToastrService
    ) {
    }

    ngOnInit(): void {
        this.initForm();
    }

    initForm(): void {
        this.newUserForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });
    }

    onLoggedin() {
        if (this.newUserForm.valid) {
            this.user.username = this.newUserForm.value.username;
            this.user.password = this.newUserForm.value.password;

            this.authService.login(this.user).subscribe({
                next: (data) => {
                    let jwToken = data.headers.get('Authorization')!;
                    this.authService.saveToken(jwToken);
                    this.toastr.success('Login successful!');
                    this.router.navigate(['/']); // Redirect to the desired route
                },
                error: (err: any) => {
                    this.err = 1;
                    this.handleLoginError(err);
                }
            });
        }
    }

    private handleLoginError(err: any): void {
        if (err.error.errorCause === 'disabled') {
            this.errorMessage = "User is disabled, please contact your administrator.";
        } else {
            this.errorMessage = "Invalid username or password.";
        }
        this.toastr.error(this.errorMessage); // Show error message using Toastr
    }

    loginWithGoogle(): void {
        this.authService.loginWithGoogle().subscribe({
            next: (data) => {
                // Check if data is not null
                if (data && data.headers) {
                    const jwToken = data.headers.get('Authorization');
                    if (jwToken) {
                        this.authService.saveToken(jwToken);
                        this.router.navigate(['/']);
                    } else {
                        this.toastr.error('Authorization token not found.');
                    }
                } else {
                    this.toastr.error('Login failed. Please try again.');
                }
            },
            error: (err: any) => {
                console.error('Google login failed:', err);
                this.toastr.error('Google login failed.'); // Show error message
            }
        });
    };

    navigateToForgotPassword() {
        this.router.navigate(['/forgot-password']);
    }
}
