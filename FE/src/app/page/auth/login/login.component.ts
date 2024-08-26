import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  loginForm !: FormGroup;
  errorMessage: string = '';

  constructor(private fb: FormBuilder, private router: Router
    //private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', Validators.compose([Validators.required, Validators.email])],
      password: ['', Validators.required]
    })
  }

  login(): void {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      // this.authService.login(email, password).subscribe(
      //   response => {
      //     console.log('Login successfull', response);
      //     this.router.navigate(['/home-page']);
      //   },
      //   errorMes => {
      //     this.errorMessage = 'Invalid login credentials';
      //   }
      // ); // chua co authService
    }
  }
}
