package com.globits.sample.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.sample.domain.PhieuNhap;
import com.globits.sample.payload.PhieuNhapDto;
import com.globits.sample.repository.PhieuNhapRepository;
import com.globits.sample.searchDto.PhieuNhapSearchDto;
import com.globits.sample.service.PhieuNhapService;
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
public class PhieuNhapServiceImp extends GenericServiceImpl<PhieuNhap, Long> implements PhieuNhapService {

    @Autowired
    private PhieuNhapRepository phieuNhapRepository;

    @Override
    public void deleteById(Long id) {
        phieuNhapRepository.deleteById(id);
    }

    @Override
    public Page<PhieuNhapDto> searchByPage(PhieuNhapSearchDto dto) {
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

        String sqlCount = "select count(entity.id) from PhieuNhap as entity where (1=1)";
        String sql = "select new com.globits.sample.payload.PhieuNhapDto(entity) from PhieuNhap as entity where (1=1)";

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword()))
            whereClause += " AND ( UPPER(entity.name) LIKE UPPER(:text) OR UPPER(entity.code) LIKE UPPER(:text) )";

        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, PhieuNhapDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            q.setParameter("text", '%' + dto.getKeyword() + '%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }

        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<PhieuNhapDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<PhieuNhapDto> result = new PageImpl<>(entities, pageable, count);

        return result;
    }

    @Override
    public PhieuNhapDto getOne(Long id) {
        PhieuNhap entity = phieuNhapRepository.getOne(id);
        if (entity != null) {
            return new PhieuNhapDto(entity);
        }
        return null;
    }

    @Override
    public PhieuNhapDto addNewOrUpdate(PhieuNhapDto dto, Long id) {
        if (dto != null) {
            PhieuNhap entity = null;
            if (id != null) {
                if (dto.getId() != null && !dto.getId().equals(id))
                    return null;
                entity = phieuNhapRepository.getOne(id);
            }
            if (entity == null)
                entity = new PhieuNhap();

            /* Set all the values */
            entity.setName(dto.getName());
            entity.setCode(dto.getCode());
            entity.setAmount(dto.getAmount());
            entity.setImport_Unit_Price(dto.getImport_Unit_Price());
            entity.setName_sp(dto.getName_sp());
            entity.setTotal_money(dto.getTotal_money());
            entity = phieuNhapRepository.save(entity);
            if (entity != null)
                return new PhieuNhapDto(entity);
        }
        return null;
    }

    @Override
    public boolean checkCodeWasUsed(String code, Long id) {
        List<PhieuNhap> list = phieuNhapRepository.findByCode(code);
        if (list != null && list.size() > 0 && list.get(0) != null && list.get(0).getId() != null) {
            if (id != null && StringUtils.hasText(id.toString()))
                if (list.get(0).getId().equals(id))
                    return false;
            return true;
        }
        return false;
    }


}
