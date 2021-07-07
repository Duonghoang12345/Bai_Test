package com.globits.sample.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.sample.domain.EnterCoupon;
import com.globits.sample.payload.EnterCouponDto;
import com.globits.sample.repository.EnterCouponRepository;
import com.globits.sample.searchDto.EnterCouponSearchDto;
import com.globits.sample.service.EnterCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EnterCouponServiceImp extends GenericServiceImpl<com.globits.sample.domain.EnterCoupon, Long> implements EnterCouponService {

    @Autowired
    private EnterCouponRepository enterCouponRepository;

    @Override
    public void deleteById(Long id) {
        enterCouponRepository.deleteById(id);
    }

    @Override
    public Page<EnterCouponDto> searchByPage(EnterCouponSearchDto dto) {
        if (dto == null) {
            return null;
        }
//        System.out.println(dto.getPageSize());
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

        String sqlCount = "select count(entity.id) from EnterCoupon as entity where (1=1)";
        String sql = "select new com.globits.sample.payload.EnterCouponDto(entity) from EnterCoupon as entity where (1=1)";

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword()))
            whereClause += " AND ( UPPER(entity.name) LIKE UPPER(:text) OR UPPER(entity.code) LIKE UPPER(:text) )";

        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, EnterCouponDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            q.setParameter("text", '%' + dto.getKeyword() + '%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }

        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<EnterCouponDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<EnterCouponDto> result = new PageImpl<>(entities, pageable, count);

        return result;
    }

    @Override
    public EnterCouponDto getOne(Long id) {
        com.globits.sample.domain.EnterCoupon entity = enterCouponRepository.getOne(id);
        if (entity != null) {
            return new EnterCouponDto(entity);
        }
        return null;
    }

    @Override
    public EnterCouponDto addNewOrUpdate(EnterCouponDto dto, Long id) {
        if (dto != null) {
            com.globits.sample.domain.EnterCoupon entity = null;
            if (id != null) {
                if (dto.getId() != null && !dto.getId().equals(id))
                    return null;
                entity = enterCouponRepository.getOne(id);
            }
            if (entity == null)
                entity = new com.globits.sample.domain.EnterCoupon();

            /* Set all the values */
            entity.setName(dto.getName());
            entity.setCode(dto.getCode());
            entity.setAmount(dto.getAmount());
            entity.setImport_Unit_Price(dto.getImport_Unit_Price());
            entity.setName_sp(dto.getName_sp());
            entity.setTotal_money(dto.getTotal_money());
            entity = enterCouponRepository.save(entity);
            if (entity != null)
                return new EnterCouponDto(entity);
        }
        return null;
    }

    @Override
    public boolean checkCodeWasUsed(String code, Long id) {
        List<EnterCoupon> list = enterCouponRepository.findByCode(code);
        if (list != null && list.size() > 0 && list.get(0) != null && list.get(0).getId() != null) {
            if (id != null && StringUtils.hasText(id.toString()))
                if (list.get(0).getId().equals(id))
                    return false;
            return true;
        }
        return false;
    }


}
