package com.globits.sample.rest;

import com.globits.sample.payload.ProductDto;
import com.globits.sample.searchDto.ProductSearchDto;
import com.globits.sample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/product")
public class RestProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/searchByPage")
    public ResponseEntity<Page<ProductDto>> searchByPage(@RequestBody ProductSearchDto searchDto) {
//        searchDto.setPageIndex(1);
//        searchDto.setPageSize(999);
        Page<ProductDto> pager = productService.searchByPage(searchDto);

        return new ResponseEntity(pager, HttpStatus.OK);
    }

    @PostMapping("/check/codeWasUsed")
    public Boolean codeWasUsed(@RequestBody ProductDto dto) {
        boolean result = true;
        if (dto.getCode() != null && StringUtils.hasText(dto.getCode()))
            result = productService.checkCodeWasUsed(dto.getCode(), dto.getId());
        return result;
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveOne(@RequestBody ProductDto dto) {
        ProductDto result = productService.addNewOrUpdate(dto, null);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateOne(@RequestBody ProductDto dto, @PathVariable Long id) {
        ProductDto result = productService.addNewOrUpdate(dto, id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable Long id) {
        ProductDto dto = productService.getOne(id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        productService.deleteById(id);

        return new ResponseEntity(true, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deletecheckMultiple/{id}")
    public ResponseEntity<Boolean> deleteCheckID(@PathVariable Long id) {
        productService.deleteById(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
