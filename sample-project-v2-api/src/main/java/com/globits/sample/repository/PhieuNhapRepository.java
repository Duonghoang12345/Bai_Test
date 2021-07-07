package com.globits.sample.repository;

import com.globits.sample.domain.PhieuNhap;
import com.globits.sample.domain.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuNhapRepository extends JpaRepository<com.globits.sample.domain.PhieuNhap, Long>, CrudRepository<com.globits.sample.domain.PhieuNhap,Long> {
    @Query("select e from PhieuNhap e where e.code = ?1")
    List<com.globits.sample.domain.PhieuNhap> findByCode(String code);

    com.globits.sample.domain.PhieuNhap findEmployeeByCode(String code);

    @Query("select count(e.id) from PhieuNhap e where e.id=?1")
    Long checkEmployee(Long id);
}
