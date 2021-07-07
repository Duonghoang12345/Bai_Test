package com.globits.sample.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.sample.domain.PhieuNhap;
import com.globits.sample.domain.SanPham;
import com.globits.sample.domain.SanPham_PhieuNhap;
import com.globits.sample.payload.PhieuNhapDto;
import com.globits.sample.payload.SanPhamDto;
import com.globits.sample.repository.PhieuNhapRepository;
import com.globits.sample.repository.SanPhamPhieuNhapRepository;
import com.globits.sample.repository.SanPhamRepository;
import com.globits.sample.searchDto.SanPhamSearchDto;
import com.globits.sample.service.SanPhamService;
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
public class SanPhamServiceImp extends GenericServiceImpl<SanPham, Long> implements SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private PhieuNhapRepository phieuNhapRepository;

    @Autowired
    private SanPhamPhieuNhapRepository sanPhamPhieuNhapRepository;

    @Override
    public void deleteById(Long id) {
        sanPhamRepository.deleteById(id);
    }

    @Override
    public Page<SanPhamDto> searchByPage(SanPhamSearchDto dto) {
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

        String sqlCount = "select count(entity.id) from SanPham as entity where (1=1)";
        String sql = "select new com.globits.sample.payload.SanPhamDto(entity) from SanPham as entity where (1=1)";
//
        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword()))
            whereClause += " AND ( UPPER(entity.name) LIKE UPPER(:text) OR UPPER(entity.code) LIKE UPPER(:text) )";

        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, SanPhamDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            q.setParameter("text", '%' + dto.getKeyword() + '%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }

        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<SanPhamDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<SanPhamDto> result = new PageImpl<>(entities, pageable, count);

        return result;
    }

    @Override
    public SanPhamDto getOne(Long id) {
        SanPham entity = sanPhamRepository.getOne(id);
        if (entity != null) {
            return new SanPhamDto(entity);
        }
        return null;
    }

    @Override
    public SanPhamDto addNewOrUpdate(SanPhamDto dto, Long id) {
        if (dto != null) {
            SanPham entity = null;
            if (id != null) {
                if (dto.getId() != null && !dto.getId().equals(id))
                    return null;
                entity = sanPhamRepository.getOne(id);
            }
            if (entity == null)
                entity = new SanPham();

            /* Set all the values */
            entity.setName(dto.getName());
            entity.setCode(dto.getCode());
            entity.setAmount(dto.getAmount());
            entity.setImport_Unit_Price(dto.getImport_Unit_Price());
            entity.setUnit_Price(dto.getUnit_Price());
            entity.setNote(dto.getNote());

            Set<SanPham_PhieuNhap> sanPham_phieuNhaps = new HashSet<>();
            Set<Long> newPhieuNhapIDs = new HashSet<>();

            if (dto.getId() != null && dto.getPhieuNhapDtos() != null && !dto.getPhieuNhapDtos().isEmpty()) {
                List<Long> collect = dto.getPhieuNhapDtos().stream().map(phieuNhap -> phieuNhap.getId())
                        .collect(Collectors.toList());

                List<Long> list = new ArrayList<>();

                for (Long PhieuNhapId : collect) {
                    SanPham_PhieuNhap employeeDepartment = sanPhamPhieuNhapRepository
                            .getSanPhamPhieuNhapFromSanPhamIdandPhieuNhapId(PhieuNhapId, dto.getId());
                    if (employeeDepartment != null) {
                        list.add(employeeDepartment.getId());
                        sanPham_phieuNhaps.add(employeeDepartment);
                    } else
                        newPhieuNhapIDs.add(PhieuNhapId);
                }

                List<Long> oldSanPhamPhieuNhapID = sanPhamPhieuNhapRepository
                        .getSanPhamPhieuNhapFromSanPhamId(dto.getId());

                List<Long> deletedIDs = oldSanPhamPhieuNhapID.stream()
                        .filter(oldId -> !list.contains(oldId)).collect(Collectors.toList());

                for (Long deletedID : deletedIDs)
                    sanPhamPhieuNhapRepository.deleteById(deletedID);

            } else if (dto.getPhieuNhapDtos() != null && !dto.getPhieuNhapDtos().isEmpty())
                newPhieuNhapIDs = dto.getPhieuNhapDtos().stream().map(phieuNhap -> phieuNhap.getId())
                        .collect(Collectors.toSet());

            entity.setSanPham_phieuNhaps(sanPham_phieuNhaps);
            entity = sanPhamRepository.save(entity);

            if (entity != null) {
                if (!newPhieuNhapIDs.isEmpty())
                    for (PhieuNhapDto newId : dto.getPhieuNhapDtos()) {
                        SanPham_PhieuNhap sanPham_phieuNhap = new SanPham_PhieuNhap();
                        PhieuNhap phieuNhap = phieuNhapRepository.getOne(newId.getId());
                        sanPham_phieuNhap.setPhieuNhap(phieuNhap);
                        sanPham_phieuNhap.setSanPham(entity);
                        sanPhamPhieuNhapRepository.save(sanPham_phieuNhap);
                    }
//
                return new SanPhamDto(entity);
            }
        }
        return null;
    }

    @Override
    public boolean checkCodeWasUsed(String code, Long id) {
        List<SanPham> list = sanPhamRepository.findByCode(code);
        if (list != null && list.size() > 0 && list.get(0) != null && list.get(0).getId() != null) {
            if (id != null && StringUtils.hasText(id.toString()))
                if (list.get(0).getId().equals(id))
                    return false;
            return true;
        }
        return false;
    }

}
