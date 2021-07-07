package com.globits.sample.repository;

import com.globits.sample.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CrudRepository<Product,Long> {
    @Query("select e from Product e where e.code = ?1")
    List<Product> findByCode(String code);

    Product findEmployeeByCode(String code);

    @Query("select count(e.id) from Product e where e.id=?1")
    Long checkEmployee(Long id);
}
