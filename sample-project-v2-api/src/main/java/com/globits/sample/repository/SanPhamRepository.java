package com.globits.sample.repository;

import com.globits.sample.domain.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long>, CrudRepository<SanPham,Long> {
    @Query("select e from SanPham e where e.code = ?1")
    List<SanPham> findByCode(String code);

    SanPham findEmployeeByCode(String code);

    @Query("select count(e.id) from SanPham e where e.id=?1")
    Long checkEmployee(Long id);
}
