package com.globits.sample.repository;

import com.globits.sample.domain.Product_EnterCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductEnterCouponRepository extends JpaRepository<Product_EnterCoupon, Long>, CrudRepository<Product_EnterCoupon,Long> {
    @Query("SELECT entity FROM Product_EnterCoupon entity WHERE entity.product.id = ?1 AND entity.enterCoupon.id = ?2")
    public Product_EnterCoupon getSanPhamPhieuNhapFromSanPhamIdandPhieuNhapId(Long sanPhamId, Long phieuNhapId);

    @Query("select entity.id From Product_EnterCoupon entity WHERE entity.product.id = ?1")
    public List<Long> getSanPhamPhieuNhapFromSanPhamId(Long sanPhamId);

    @Query("select entity.id From Product_EnterCoupon entity WHERE entity.enterCoupon.id = ?1")
    public List<Long> getSanPhamPhieuNhapFromPhieuNhapId(Long phieuNhapId);
}
