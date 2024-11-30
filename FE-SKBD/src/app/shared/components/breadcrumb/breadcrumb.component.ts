import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterModule } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { BreadcrumbModule } from 'primeng/breadcrumb';
@Component({
  selector: 'app-breadcrumb',
  standalone: true,
  imports: [BreadcrumbModule, RouterModule],
  templateUrl: './breadcrumb.component.html',
  styleUrl: './breadcrumb.component.css',
})
export class BreadcrumbComponentDemo implements OnInit {
  items!: MenuItem[];
  homeItem: MenuItem = { icon: 'pi pi-home', routerLink: '/' };
  constructor(private router: Router) {}

  ngOnInit() {
    this.items = [];

    // Listen for route changes
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.updateBreadcrumb();
      }
    });
  }

  updateBreadcrumb() {
    const urlSegments = this.router.url.split('/').filter((segment) => segment);

    // Reset the breadcrumb items array
    this.items = urlSegments.map((segment, index) => {
      return {
        label: this.formatSegment(segment),
        routerLink: `/${urlSegments.slice(0, index + 1).join('/')}`,
      };
    });
  }

  formatSegment(segment: string): string {
    // Format the segment (e.g., capitalize the first letter)
    return segment.charAt(0).toUpperCase() + segment.slice(1);
  }
}
