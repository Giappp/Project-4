import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { Observable } from 'rxjs';
import { Category } from '../../model/category';
import { CarouselModule } from 'primeng/carousel';
import { GalleriaModule } from 'primeng/galleria';
@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [CommonModule, CarouselModule, GalleriaModule],
  providers: [],
})
export class HomeComponent implements OnInit {
  @ViewChild('carousel') carousel!: any;
  categories$!: Observable<Category[]>;
  carouselImageUrl!: any[];

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

  constructor() {}
  ngOnInit(): void {
    this.carouselImageUrl = [
      '/assets/images/17277697730331009.webp',
      '/assets/images/17150541229617388.webp',
      '/assets/images/17289654441295587.webp',
      '/assets/images/17291301779762643.webp',
    ];
  }
}
