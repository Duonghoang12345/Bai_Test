package com.globits.sample.payload;

import com.globits.sample.domain.SanPham_PhieuNhap;

public class SanPhamPhieuNhapDto {
    private com.globits.sample.payload.SanPhamDto sanPhamDto;
    private com.globits.sample.payload.PhieuNhapDto phieuNhapDto;

    public com.globits.sample.payload.SanPhamDto getSanPhamDto() {
        return sanPhamDto;
    }

    public void setSanPhamDto(com.globits.sample.payload.SanPhamDto sanPhamDto) {
        this.sanPhamDto = sanPhamDto;
    }

    public com.globits.sample.payload.PhieuNhapDto getPhieuNhapDto() {
        return phieuNhapDto;
    }

    public void setPhieuNhapDto(com.globits.sample.payload.PhieuNhapDto phieuNhapDto) {
        this.phieuNhapDto = phieuNhapDto;
    }

    public SanPhamPhieuNhapDto(SanPham_PhieuNhap entity) {
        super();
        if (entity != null) {
            this.sanPhamDto = new SanPhamDto(entity.getSanPham());
            this.phieuNhapDto = new PhieuNhapDto(entity.getPhieuNhap());
        }
    }
}
