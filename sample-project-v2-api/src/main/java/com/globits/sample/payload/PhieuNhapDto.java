package com.globits.sample.payload;

import com.globits.sample.domain.PhieuNhap;
import com.globits.sample.domain.SanPham_PhieuNhap;
import com.globits.sample.payload.BaseObjectDto;
import com.globits.sample.payload.SanPhamDto;

import java.util.HashSet;
import java.util.Set;

public class PhieuNhapDto extends BaseObjectDto{
    private String code;
    private String name;
    private String name_sp;//tensanpham
    private int amount;//soluong
    private float import_Unit_Price;//dongianhap
    private float total_money;//tongtien
    private Set<SanPhamDto> sanPhamDto;

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

    public Set<SanPhamDto> getSanPhamDto() {
        return sanPhamDto;
    }

    public void setSanPhamDto(Set<SanPhamDto> sanPhamDto) {
        this.sanPhamDto = sanPhamDto;
    }

    public PhieuNhapDto(){}

    public PhieuNhapDto(PhieuNhap entity) {
        super();
        if (entity != null) {
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.name_sp = entity.getName_sp();//tensanpham
            this.amount = entity.getAmount();//soluong
            this.import_Unit_Price = entity.getImport_Unit_Price();//dongianhap
            this.total_money = entity.getTotal_money();

            if (entity.getSanPham_phieuNhaps() != null && entity.getSanPham_phieuNhaps().size() > 0) {
                sanPhamDto  = new HashSet<>();
                for (SanPham_PhieuNhap sanPham_phieuNhap : entity.getSanPham_phieuNhaps()) {
                    SanPhamDto dto = new SanPhamDto(sanPham_phieuNhap.getSanPham(),true);
                    sanPhamDto.add(dto);
                }
            }
        }
    }


    public PhieuNhapDto(PhieuNhap entity, boolean simple) {
        super();
        if (entity != null) {
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.name_sp = entity.getName_sp();//tensanpham
            this.amount=entity.getAmount();//soluong
            this.import_Unit_Price = entity.getImport_Unit_Price();//dongianhap
            this.total_money = entity.getTotal_money();
        }
    }
}
