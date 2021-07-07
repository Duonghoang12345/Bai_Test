package com.globits.sample.domain;

import com.globits.sample.domain.BaseObject;
import com.globits.sample.domain.SanPham_PhieuNhap;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement
@Entity
@Table(name = "tbl_SanPham")
public class SanPham extends BaseObject {
    private static final long serialVersionUID = 1L;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

    @Column(name="amount")
    private int amount;

    @Column(name="import_unit_price")
    private float import_Unit_Price;

    @Column(name="unit_price")
    private float unit_Price;

    @Column(name="note")
    private String note;

    @OneToMany(mappedBy = "sanPham", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    public Set<SanPham_PhieuNhap> getSanPham_phieuNhaps() {
        return sanPham_phieuNhaps;
    }

    public void setSanPham_phieuNhaps(Set<SanPham_PhieuNhap> sanPham_phieuNhaps) {
        this.sanPham_phieuNhaps = sanPham_phieuNhaps;
    }
}
