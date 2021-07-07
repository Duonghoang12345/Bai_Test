package com.globits.sample.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.sample.domain.Product;
import com.globits.sample.domain.Product_EnterCoupon;
import com.globits.sample.payload.EnterCouponDto;
import com.globits.sample.payload.ProductDto;
import com.globits.sample.repository.EnterCouponRepository;
import com.globits.sample.repository.ProductEnterCouponRepository;
import com.globits.sample.repository.ProductRepository;
import com.globits.sample.searchDto.ProductSearchDto;
import com.globits.sample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImp extends GenericServiceImpl<Product, Long> implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EnterCouponRepository enterCouponRepository;

    @Autowired
    private ProductEnterCouponRepository productEnterCouponRepository;

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDto> searchByPage(ProductSearchDto dto) {
        if (dto == null) {
            return null;
        }
        System.out.println(dto.getPageSize());
        int pageIndex = dto.getPageIndex();
        int pageSize = dto.getPageSize();
        if (pageIndex > 0)
            pageIndex--;
        else
            pageIndex = 0;

        String whereClause = "";


        String orderBy = " ORDER BY entity.name DESC";
        if (dto.getCode() != null && StringUtils.hasLength(dto.getCode().toString()))
            orderBy = " ORDER BY entity." + dto.getCode() + " ASC";

        String sqlCount = "select count(entity.id) from Product as entity where (1=1)";
        String sql = "select new com.globits.sample.payload.ProductDto(entity) from Product as entity where (1=1)";
//
        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword()))
            whereClause += " AND ( UPPER(entity.name) LIKE UPPER(:text) OR UPPER(entity.code) LIKE UPPER(:text) )";

        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, ProductDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            q.setParameter("text", '%' + dto.getKeyword() + '%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }

        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<ProductDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<ProductDto> result = new PageImpl<>(entities, pageable, count);

        return result;
    }

    @Override
    public ProductDto getOne(Long id) {
        Product entity = productRepository.getOne(id);
        if (entity != null) {
            return new ProductDto(entity);
        }
        return null;
    }

    @Override
    public ProductDto addNewOrUpdate(ProductDto dto, Long id) {
        if (dto != null) {
            Product entity = null;
            if (id != null) {
                if (dto.getId() != null && !dto.getId().equals(id))
                    return null;
                entity = productRepository.getOne(id);
            }
            if (entity == null)
                entity = new Product();

            /* Set all the values */
            entity.setName(dto.getName());
            entity.setCode(dto.getCode());
            entity.setAmount(dto.getAmount());
            entity.setImport_Unit_Price(dto.getImport_Unit_Price());
            entity.setUnit_Price(dto.getUnit_Price());
            entity.setNote(dto.getNote());

            Set<Product_EnterCoupon> productEnterCoupons = new HashSet<>();
            Set<Long> newPhieuNhapIDs = new HashSet<>();

            if (dto.getId() != null && dto.getPhieuNhapDtos() != null && !dto.getPhieuNhapDtos().isEmpty()) {
                List<Long> collect = dto.getPhieuNhapDtos().stream().map(phieuNhap -> phieuNhap.getId())
                        .collect(Collectors.toList());

                List<Long> list = new ArrayList<>();

                for (Long PhieuNhapId : collect) {
                    Product_EnterCoupon employeeDepartment = productEnterCouponRepository
                            .getSanPhamPhieuNhapFromSanPhamIdandPhieuNhapId(PhieuNhapId, dto.getId());
                    if (employeeDepartment != null) {
                        list.add(employeeDepartment.getId());
                        productEnterCoupons.add(employeeDepartment);
                    } else
                        newPhieuNhapIDs.add(PhieuNhapId);
                }

                List<Long> oldSanPhamPhieuNhapID = productEnterCouponRepository
                        .getSanPhamPhieuNhapFromSanPhamId(dto.getId());

                List<Long> deletedIDs = oldSanPhamPhieuNhapID.stream()
                        .filter(oldId -> !list.contains(oldId)).collect(Collectors.toList());

                for (Long deletedID : deletedIDs)
                    productEnterCouponRepository.deleteById(deletedID);

            } else if (dto.getPhieuNhapDtos() != null && !dto.getPhieuNhapDtos().isEmpty())
                newPhieuNhapIDs = dto.getPhieuNhapDtos().stream().map(phieuNhap -> phieuNhap.getId())
                        .collect(Collectors.toSet());

            entity.setProductEnterCoupons(productEnterCoupons);
            entity = productRepository.save(entity);

            if (entity != null) {
                if (!newPhieuNhapIDs.isEmpty())
                    for (EnterCouponDto newId : dto.getPhieuNhapDtos()) {
                        Product_EnterCoupon productEnterCoupon = new Product_EnterCoupon();
                        com.globits.sample.domain.EnterCoupon enterCoupon = enterCouponRepository.getOne(newId.getId());
                        productEnterCoupon.setEnterCoupon(enterCoupon);
                        productEnterCoupon.setProduct(entity);
                        productEnterCouponRepository.save(productEnterCoupon);
                    }
//
                return new ProductDto(entity);
            }
        }
        return null;
    }

    @Override
    public boolean checkCodeWasUsed(String code, Long id) {
        List<Product> list = productRepository.findByCode(code);
        if (list != null && list.size() > 0 && list.get(0) != null && list.get(0).getId() != null) {
            if (id != null && StringUtils.hasText(id.toString()))
                if (list.get(0).getId().equals(id))
                    return false;
            return true;
        }
        return false;
    }

}
