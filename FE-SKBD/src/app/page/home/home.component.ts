import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { map, Observable } from 'rxjs';
import { Category } from '../../model/category';
import { CarouselModule } from 'primeng/carousel';
import { GalleriaModule } from 'primeng/galleria';
import { HomeService } from './home-service.service';
import { NewArrivalsProducts } from '../../model/new-arrivals';
import { RouterModule } from '@angular/router';
@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [CommonModule, CarouselModule, GalleriaModule, RouterModule],
  providers: [],
})
export class HomeComponent implements OnInit {
  @ViewChild('carousel') carousel!: any;
  categories$!: Observable<Category[]>;
  carouselImageUrl!: any[];

  private readonly homeService = inject(HomeService);
  newArrivals: NewArrivalsProducts[] = [];

  responsiveOptions: any[] = [
    {
      breakpoint: '1024px',
      numVisible: 5,
    },
    {
      breakpoint: '768px',
      numVisible: 3,
    },
    {
      breakpoint: '560px',
      numVisible: 1,
    },
  ];

  constructor() {
    this.homeService.getNewArrivalsProducts().subscribe((response: any) => {
      console.log(response);
      this.newArrivals = response.data;
    });
  }
  ngOnInit(): void {
    this.carouselImageUrl = [
      '/assets/images/17277697730331009.webp',
      '/assets/images/17150541229617388.webp',
      '/assets/images/17289654441295587.webp',
      '/assets/images/17291301779762643.webp',
    ];
  }
}
