package com.globits.sample.domain;

import com.globits.sample.domain.BaseObject;
import com.globits.sample.domain.PhieuNhap;
import com.globits.sample.domain.SanPham;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Table(name = "tbl_Sanpham_Phieunhap")
@Entity
public class SanPham_PhieuNhap extends BaseObject {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="sanpham_id")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name="phieunhap_id")
    private PhieuNhap phieuNhap;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public PhieuNhap getPhieuNhap() {
        return phieuNhap;
    }

    public void setPhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhap = phieuNhap;
    }
}
