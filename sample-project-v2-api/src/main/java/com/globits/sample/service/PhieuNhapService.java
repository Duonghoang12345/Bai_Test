package com.globits.sample.service;

import com.globits.core.service.GenericService;

import com.globits.sample.domain.PhieuNhap;
import com.globits.sample.payload.PhieuNhapDto;
import com.globits.sample.searchDto.PhieuNhapSearchDto;
import org.springframework.data.domain.Page;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface PhieuNhapService extends GenericService<PhieuNhap, Long> {
    void deleteById(Long id);

    Page<PhieuNhapDto> searchByPage(PhieuNhapSearchDto dto);

    PhieuNhapDto getOne(Long id);

    PhieuNhapDto addNewOrUpdate(PhieuNhapDto dto, Long id);

    boolean checkCodeWasUsed(String code,Long id);

//    ByteArrayInputStream empToExcel(List<PhieuNhapDto> empDto) throws IOException;
//
//    boolean deleteCheckById(Long id);
}
