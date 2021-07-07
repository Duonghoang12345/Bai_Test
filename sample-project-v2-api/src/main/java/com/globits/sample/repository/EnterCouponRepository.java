package com.globits.sample.repository;

import com.globits.sample.domain.EnterCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterCouponRepository extends JpaRepository<EnterCoupon, Long>, CrudRepository<EnterCoupon,Long> {
    @Query("select e from EnterCoupon e where e.code = ?1")
    List<EnterCoupon> findByCode(String code);

    EnterCoupon findEmployeeByCode(String code);

    @Query("select count(e.id) from EnterCoupon e where e.id=?1")
    Long checkEmployee(Long id);
}
