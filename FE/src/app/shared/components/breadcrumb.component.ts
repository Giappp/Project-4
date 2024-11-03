import { Component } from '@angular/core';
import { NzBreadCrumbModule } from 'ng-zorro-antd/breadcrumb';
import { BreadcrumbService } from '../services/breadcrumb-service.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-breadcrumb',
  standalone: true,
  imports: [NzBreadCrumbModule, CommonModule, RouterModule],
  template: `
    <nz-breadcrumb *ngIf="history.length > 1">
      <nz-breadcrumb-item *ngFor="let url of history; let i = index">
        <!-- Exclude current page -->
        <a [routerLink]="url">{{ getUrlLabel(url) }}</a> &gt;
        <!-- Customize separator if needed -->
        <nz-breadcrumb-separator></nz-breadcrumb-separator>
      </nz-breadcrumb-item>
    </nz-breadcrumb>
  `,
})
export class NzDemoBreadcrumbRouterComponent {
  history: string[] = [];

  constructor(private breadcrumbService: BreadcrumbService) {}

  ngOnInit(): void {
    this.history = this.breadcrumbService.getHistory();
  }

  // Optional helper to format breadcrumb labels (or customize based on route)
  getUrlLabel(url: string): string {
    return url.split('/').pop() || 'Home';
  }
}
