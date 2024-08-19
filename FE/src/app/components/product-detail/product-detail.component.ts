import { Component, OnInit } from '@angular/core';
import { ProductDetailService } from '../service/product-detail.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent implements OnInit {
  product: any;
  selectedColor: string | null = null;
  selectedSize: string | null = null;
  quantity: number = 1;
  selectedCombo: any;

  constructor(
    private productService: ProductDetailService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const productId = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProductById(productId).subscribe(data => {
      this.product = data;
    });

    // const products = [
    //   {
    //     id: 1,
    //     name: "Sản phẩm A",
    //     reviewsCount: 4,
    //     rating: 4.5,
    //     originalPrice: 100000,
    //     discountedPrice: 20000,
    //     comboDeals: ["mua 1 tang 10", "mua 1000 tra tien 2000"],
    //     img: "https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/15efd399b216463e9392af5700c52792_9366/Giay_Superstar_Adifom_trang_HQ8750_01_standard.jpg",
    //     colors: ["Red", "Blue", "Green"],
    //     size: ["S", "M", "L", "XL", "XXL"],
    //     description: "Sản phẩm A là đôi giày thể thao với thiết kế hiện đại và phong cách trẻ trung. Chất liệu vải cao cấp, bền bỉ và thoáng khí giúp bạn cảm thấy thoải mái trong suốt cả ngày. Đế giày có độ bám tốt, phù hợp cho các hoạt động thể thao và dạo phố. Với nhiều màu sắc và kích cỡ lựa chọn, sản phẩm này dễ dàng phối hợp với nhiều trang phục khác nhau. Đây là lựa chọn hoàn hảo cho những ai yêu thích sự kết hợp giữa thời trang và tiện ích."
    //   },
    //   {
    //     id: 2,
    //     name: "Sản phẩm B",
    //     price: 150000,
    //     img: "https://bizweb.dktcdn.net/thumb/1024x1024/100/427/145/products/z4510556128592-61d4f66741825e48d2c80b0851a73777.jpg?v=1689165521347",
    //     colors: ["Black", "White"],
    //     size: ["M", "L", "XL"],
    //     description: "Sản phẩm B là một đôi giày với thiết kế tối giản và thanh lịch, phù hợp với nhiều phong cách khác nhau. Chất liệu da cao cấp và đế giày có độ bám tốt, tạo sự thoải mái và bảo vệ cho đôi chân trong suốt cả ngày. Màu sắc cơ bản như đen và trắng dễ dàng phối hợp với nhiều trang phục khác nhau, từ đồ công sở đến trang phục thường ngày. Đây là sự lựa chọn lý tưởng cho những ai tìm kiếm sự đơn giản nhưng vẫn nổi bật và thời trang."
    //   }
    // ];


    // this.product = products.find(product => product.id === productId);
  }

  addToCart() {
    if (!this.selectedColor) {
      alert("Please choose color");
    } else if (!this.selectedSize) {
      alert("Please choose your size");
    } else {
      alert("Cart added successfully")
    }
    return;
  }

  buyNow() {
    if (!this.selectedColor) {
      alert("Please choose color");
    } else if (!this.selectedSize) {
      alert("Please choose your size");
    } else {
      alert("Buy successfully")
    }
  }

  decreaseQuantity() {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  increaseQuantity() {
    this.quantity++;
  }
}
