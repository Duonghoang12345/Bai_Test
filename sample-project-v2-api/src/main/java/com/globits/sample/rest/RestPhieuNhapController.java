package com.globits.sample.rest;

import com.globits.sample.payload.PhieuNhapDto;
import com.globits.sample.payload.SanPhamDto;
import com.globits.sample.searchDto.PhieuNhapSearchDto;
import com.globits.sample.service.PhieuNhapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/phieunhap")
public class RestPhieuNhapController {
    @Autowired
    private PhieuNhapService phieuNhapService;

    @PostMapping("/searchByPage")
    public ResponseEntity<Page<PhieuNhapDto>> searchByPage(@RequestBody PhieuNhapSearchDto searchDto) {
//        searchDto.setPageIndex(1);
//        searchDto.setPageSize(999);
        Page<PhieuNhapDto> pager = phieuNhapService.searchByPage(searchDto);

        return new ResponseEntity(pager, HttpStatus.OK);
    }

    @PostMapping("/check/codeWasUsed")
    public Boolean codeWasUsed(@RequestBody SanPhamDto dto) {
        boolean result = true;
        if (dto.getCode() != null && StringUtils.hasText(dto.getCode()))
            result = phieuNhapService.checkCodeWasUsed(dto.getCode(), dto.getId());
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuNhapDto> getOne(@PathVariable Long id) {
        PhieuNhapDto dto = phieuNhapService.getOne(id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PhieuNhapDto> saveOne(@RequestBody PhieuNhapDto dto) {
        PhieuNhapDto result = phieuNhapService.addNewOrUpdate(dto, null);

        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhieuNhapDto> updateOne(@RequestBody PhieuNhapDto dto,@PathVariable Long id) {
        PhieuNhapDto result = phieuNhapService.addNewOrUpdate(dto, id);

        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        phieuNhapService.deleteById(id);

        return new ResponseEntity(true, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deletecheckMultiple/{id}")
    public ResponseEntity<Boolean> deleteCheckID(@PathVariable Long id) {
        phieuNhapService.deleteById(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
