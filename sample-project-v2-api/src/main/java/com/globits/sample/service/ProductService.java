package com.globits.sample.service;

import com.globits.core.service.GenericService;
import com.globits.sample.domain.Product;
import com.globits.sample.payload.ProductDto;
import com.globits.sample.searchDto.ProductSearchDto;
import org.springframework.data.domain.Page;

public interface ProductService extends GenericService<Product, Long> {
    void deleteById(Long id);

    Page<ProductDto> searchByPage(ProductSearchDto dto);

    ProductDto getOne(Long id);

    ProductDto addNewOrUpdate(ProductDto dto, Long id);

    boolean checkCodeWasUsed(String code,Long id);

//    ByteArrayInputStream empToExcel(List<SanPhamDto> empDto) throws IOException;

//    boolean deleteCheckById(Long id);
}
