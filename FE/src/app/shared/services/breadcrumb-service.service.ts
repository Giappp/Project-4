import { Injectable } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class BreadcrumbService {
  private history: string[] = [];
  constructor(private router: Router) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        if (this.history[this.history.length - 1] !== event.urlAfterRedirects) {
          this.history.push(event.urlAfterRedirects);
        }
      }
    });
  }
  getHistory(): string[] {
    return this.history;
  }

  goBack(): void {
    this.history.pop(); // Remove current route
    const previousUrl = this.history.pop(); // Get previous route
    if (previousUrl) {
      this.router.navigateByUrl(previousUrl);
    }
  }
}
