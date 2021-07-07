package com.globits.sample.payload;

import com.globits.sample.domain.Product;
import com.globits.sample.domain.Product_EnterCoupon;

import java.util.HashSet;
import java.util.Set;

public class ProductDto extends BaseObjectDto{
    private String code;
    private String name;
    private int amount;//soluong
    private float import_Unit_Price;//dongianhap
    private float unit_Price;//dongiaban
    private String note;//ghi chu
    private Set<EnterCouponDto> phieuNhapDtos ;

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

    public float getUnit_Price() {
        return unit_Price;
    }

    public void setUnit_Price(float unit_Price) {
        this.unit_Price = unit_Price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<EnterCouponDto> getPhieuNhapDtos() {
        return phieuNhapDtos;
    }

    public void setPhieuNhapDtos(Set<EnterCouponDto> phieuNhapDtos) {
        this.phieuNhapDtos = phieuNhapDtos;
    }

    public ProductDto(){

    }

    public ProductDto(Product entity) {
        super();
        if (entity != null) {
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.import_Unit_Price = entity.getImport_Unit_Price();
            this.amount = entity.getAmount();
            this.unit_Price = entity.getUnit_Price();
            this.note = entity.getNote();

            if (entity.getProductEnterCoupons() != null && entity.getProductEnterCoupons().size() > 0) {
                phieuNhapDtos  = new HashSet<>();
                for (Product_EnterCoupon productEnterCoupon : entity.getProductEnterCoupons()) {
                    EnterCouponDto dto = new EnterCouponDto(productEnterCoupon.getEnterCoupon(),true);
                    phieuNhapDtos.add(dto);
                }
            }
        }
    }

    public ProductDto(Product entity, boolean simple) {
        super();
        if (entity != null) {
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.import_Unit_Price = entity.getImport_Unit_Price();
            this.amount = entity.getAmount();
            this.unit_Price = entity.getUnit_Price();
            this.note = entity.getNote();
        }
    }
}
