package com.globits.sample.domain;

import com.globits.sample.domain.BaseObject;
import com.globits.sample.domain.SanPham_PhieuNhap;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement
@Entity
@Table(name = "tbl_PhieuNhap")
public class PhieuNhap extends BaseObject{
    private static final long serialVersionUID = 1L;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

    @Column(name="name_sp")
    private String name_sp;

    @Column(name="amount")
    private int amount;

    @Column(name="import_unit_price")
    private float import_Unit_Price;

    @Column(name="total_money")
    private float total_money;

    @OneToMany(mappedBy = "phieuNhap", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<SanPham_PhieuNhap> sanPham_phieuNhaps;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public Set<SanPham_PhieuNhap> getSanPham_phieuNhaps() {
        return sanPham_phieuNhaps;
    }

    public void setSanPham_phieuNhaps(Set<SanPham_PhieuNhap> sanPham_phieuNhaps) {
        this.sanPham_phieuNhaps = sanPham_phieuNhaps;
    }
}
