package com.globits.sample.service;

import com.globits.core.service.GenericService;
import com.globits.sample.domain.SanPham;
import com.globits.sample.payload.SanPhamDto;
import com.globits.sample.searchDto.SanPhamSearchDto;
import org.springframework.data.domain.Page;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface SanPhamService extends GenericService<SanPham, Long> {
    void deleteById(Long id);

    Page<com.globits.sample.payload.SanPhamDto> searchByPage(SanPhamSearchDto dto);

    com.globits.sample.payload.SanPhamDto getOne(Long id);

    com.globits.sample.payload.SanPhamDto addNewOrUpdate(SanPhamDto dto, Long id);

    boolean checkCodeWasUsed(String code,Long id);

//    ByteArrayInputStream empToExcel(List<SanPhamDto> empDto) throws IOException;

//    boolean deleteCheckById(Long id);
}
