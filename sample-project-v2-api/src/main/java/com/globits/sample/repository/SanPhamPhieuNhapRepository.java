package com.globits.sample.repository;

import com.globits.sample.domain.SanPham;
import com.globits.sample.domain.SanPham_PhieuNhap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamPhieuNhapRepository extends JpaRepository<SanPham_PhieuNhap, Long>, CrudRepository<SanPham_PhieuNhap,Long> {
    @Query("SELECT entity FROM SanPham_PhieuNhap entity WHERE entity.sanPham.id = ?1 AND entity.phieuNhap.id = ?2")
    public SanPham_PhieuNhap getSanPhamPhieuNhapFromSanPhamIdandPhieuNhapId(Long sanPhamId, Long phieuNhapId);

    @Query("select entity.id From SanPham_PhieuNhap entity WHERE entity.sanPham.id = ?1")
    public List<Long> getSanPhamPhieuNhapFromSanPhamId(Long sanPhamId);

    @Query("select entity.id From SanPham_PhieuNhap entity WHERE entity.phieuNhap.id = ?1")
    public List<Long> getSanPhamPhieuNhapFromPhieuNhapId(Long phieuNhapId);
}
