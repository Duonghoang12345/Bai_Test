package com.globits.sample.rest;

import com.globits.sample.payload.EnterCouponDto;
import com.globits.sample.payload.ProductDto;
import com.globits.sample.searchDto.EnterCouponSearchDto;
import com.globits.sample.service.EnterCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/enter_coupon")
public class RestEnterCouponController {
    @Autowired
    private EnterCouponService enterCouponService;

    @PostMapping("/searchByPage")
    public ResponseEntity<Page<EnterCouponDto>> searchByPage(@RequestBody EnterCouponSearchDto searchDto) {
//        searchDto.setPageIndex(1);
//        searchDto.setPageSize(999);
        Page<EnterCouponDto> pager = enterCouponService.searchByPage(searchDto);

        return new ResponseEntity(pager, HttpStatus.OK);
    }

    @PostMapping("/check/codeWasUsed")
    public Boolean codeWasUsed(@RequestBody ProductDto dto) {
        boolean result = true;
        if (dto.getCode() != null && StringUtils.hasText(dto.getCode()))
            result = enterCouponService.checkCodeWasUsed(dto.getCode(), dto.getId());
        return result;
    }

    @PostMapping
    public ResponseEntity<EnterCouponDto> saveOne(@RequestBody EnterCouponDto dto) {
        EnterCouponDto result = enterCouponService.addNewOrUpdate(dto, null);

        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnterCouponDto> updateOne(@RequestBody EnterCouponDto dto, @PathVariable Long id) {
        EnterCouponDto result = enterCouponService.addNewOrUpdate(dto, id);

        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnterCouponDto> getOne(@PathVariable Long id) {
        EnterCouponDto dto = enterCouponService.getOne(id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        enterCouponService.deleteById(id);

        return new ResponseEntity(true, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deletecheckMultiple/{id}")
    public ResponseEntity<Boolean> deleteCheckID(@PathVariable Long id) {
        enterCouponService.deleteById(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
