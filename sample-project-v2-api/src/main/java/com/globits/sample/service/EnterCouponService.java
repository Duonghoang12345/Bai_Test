package com.globits.sample.service;

import com.globits.core.service.GenericService;

import com.globits.sample.domain.EnterCoupon;
import com.globits.sample.payload.EnterCouponDto;
import com.globits.sample.searchDto.EnterCouponSearchDto;
import org.springframework.data.domain.Page;

public interface EnterCouponService extends GenericService<EnterCoupon, Long> {
    void deleteById(Long id);

    Page<EnterCouponDto> searchByPage(EnterCouponSearchDto dto);

    EnterCouponDto getOne(Long id);

    EnterCouponDto addNewOrUpdate(EnterCouponDto dto, Long id);

    boolean checkCodeWasUsed(String code,Long id);

//    ByteArrayInputStream empToExcel(List<PhieuNhapDto> empDto) throws IOException;
//
//    boolean deleteCheckById(Long id);
}
