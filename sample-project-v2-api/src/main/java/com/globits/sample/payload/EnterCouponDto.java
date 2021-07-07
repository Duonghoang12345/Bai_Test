package com.globits.sample.payload;

import com.globits.sample.domain.Product_EnterCoupon;

import java.util.HashSet;
import java.util.Set;

public class EnterCouponDto extends BaseObjectDto {
    private String code;
    private String name;
    private String name_sp;//tensanpham
    private int amount;//soluong
    private float import_Unit_Price;//dongianhap
    private float total_money;//tongtien
    private Set<ProductDto> productDto;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_sp() {
        return name_sp;
    }

    public void setName_sp(String name_sp) {
        this.name_sp = name_sp;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getImport_Unit_Price() {
        return import_Unit_Price;
    }

    public void setImport_Unit_Price(float import_Unit_Price) {
        this.import_Unit_Price = import_Unit_Price;
    }

    public float getTotal_money() {
        return total_money;
    }

    public void setTotal_money(float total_money) {
        this.total_money = total_money;
    }

    public Set<ProductDto> getProductDto() {
        return productDto;
    }

    public void setProductDto(Set<ProductDto> productDto) {
        this.productDto = productDto;
    }

    public EnterCouponDto() {
    }

    public EnterCouponDto(com.globits.sample.domain.EnterCoupon entity) {
        super();
        if (entity != null) {
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.name_sp = entity.getName_sp();//tensanpham
            this.amount = entity.getAmount();//soluong
            this.import_Unit_Price = entity.getImport_Unit_Price();//dongianhap
            this.total_money = entity.getTotal_money();

            if (entity.getProductEnterCoupons() != null && entity.getProductEnterCoupons().size() > 0) {
                productDto = new HashSet<>();
                for (Product_EnterCoupon productEnterCoupon : entity.getProductEnterCoupons()) {
                    ProductDto dto = new ProductDto(productEnterCoupon.getProduct(), true);
                    productDto.add(dto);
                }
            }
        }
    }


    public EnterCouponDto(com.globits.sample.domain.EnterCoupon entity, boolean simple) {
        super();
        if (entity != null) {
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.name_sp = entity.getName_sp();//tensanpham
            this.amount = entity.getAmount();//soluong
            this.import_Unit_Price = entity.getImport_Unit_Price();//dongianhap
            this.total_money = entity.getTotal_money();
        }
    }
}
