package com.globits.sample.rest;

import com.globits.sample.payload.SanPhamDto;
import com.globits.sample.searchDto.SanPhamSearchDto;
import com.globits.sample.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/sanpham")
public class RestSanPhamController {
    @Autowired
    private SanPhamService sanPhamService;

    @PostMapping("/searchByPage")
    public ResponseEntity<Page<SanPhamDto>> searchByPage(@RequestBody SanPhamSearchDto searchDto) {
//        searchDto.setPageIndex(1);
//        searchDto.setPageSize(999);
        Page<SanPhamDto> pager = sanPhamService.searchByPage(searchDto);

        return new ResponseEntity(pager, HttpStatus.OK);
    }

    @PostMapping("/check/codeWasUsed")
    public Boolean codeWasUsed(@RequestBody SanPhamDto dto) {
        boolean result = true;
        if (dto.getCode() != null && StringUtils.hasText(dto.getCode()))
            result = sanPhamService.checkCodeWasUsed(dto.getCode(), dto.getId());
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamDto> getOne(@PathVariable Long id) {
        SanPhamDto dto = sanPhamService.getOne(id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SanPhamDto> saveOne(@RequestBody SanPhamDto dto) {
        SanPhamDto result = sanPhamService.addNewOrUpdate(dto, null);

        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SanPhamDto> updateOne(@RequestBody SanPhamDto dto,@PathVariable Long id) {
        SanPhamDto result = sanPhamService.addNewOrUpdate(dto, id);

        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        sanPhamService.deleteById(id);

        return new ResponseEntity(true, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deletecheckMultiple/{id}")
    public ResponseEntity<Boolean> deleteCheckID(@PathVariable Long id) {
        sanPhamService.deleteById(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
