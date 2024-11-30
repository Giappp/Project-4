import { Component, OnInit } from '@angular/core';
import { User } from '../interfaces/User';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css' ]  // Corrected to 'styleUrls'
})
export class RegisterComponent implements OnInit {
  public user = new User();
  err: any ;
  loading: boolean = false;

  confirmPassword?: string;
  myForm!: FormGroup;

  constructor(
      private formBuilder: FormBuilder,
      private authService: AuthService,
      private router: Router,
      private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.myForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(3)]],
      confirmPassword: ['', [Validators.required]]
    });
  }

  onRegister() {

    this.user.username = this.myForm.value.username;
    this.user.email = this.myForm.value.email;
    this.user.password = this.myForm.value.password;

    console.log(this.user);
    this.loading = true;

    this.authService.registerUser(this.user).subscribe({
      next: (res) => {
        this.authService.setRegisteredUser(this.user);
        this.loading = false;

        this.toastr.success('Please confirm your email', 'Confirmation'); // Translated message
        this.router.navigate(['/verifEmail', this.user.email]);
      },
      error: (err: any) => {
        this.loading = false; // Stop loading on error
        if (err.status === 400) {
          this.err = err.error.message;
        } else {
          this.err = 'An unexpected error occurred. Please try again.'; // Generic error message
        }
      }
    });
  }
}