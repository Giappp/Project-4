import { Component , OnInit} from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  constructor(private router: Router){}

  ngOnInit() {
    const token = localStorage.getItem('token'); // Check for JWT token
    if (!token) {
        this.router.navigate(['/login']); // Redirect to login if no token
    } else {
        this.router.navigate(['/home']); // Optional: Redirect to home if token exists
    }
}
  title = 'FE-fb';
}
