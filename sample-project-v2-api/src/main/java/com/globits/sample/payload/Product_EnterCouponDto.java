package com.globits.sample.payload;

import com.globits.sample.domain.Product_EnterCoupon;

public class Product_EnterCouponDto {
    private ProductDto productDto;
    private EnterCouponDto enterCouponDto;

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public EnterCouponDto getEnterCouponDto() {
        return enterCouponDto;
    }

    public void setEnterCouponDto(EnterCouponDto enterCouponDto) {
        this.enterCouponDto = enterCouponDto;
    }

    public Product_EnterCouponDto() {
    }

    public Product_EnterCouponDto(Product_EnterCoupon entity) {
        super();
        if (entity != null) {
            this.productDto = new ProductDto(entity.getProduct());
            this.enterCouponDto = new EnterCouponDto(entity.getEnterCoupon());
        }
    }
}
