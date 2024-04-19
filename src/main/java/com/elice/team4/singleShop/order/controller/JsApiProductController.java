package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.ProductOrderDto;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JsApiProductController {

    private final ProductRepository productRepository;
    @GetMapping("products/api/products/{id}")
    public ResponseEntity<ProductOrderDto> getProduct(@PathVariable(name = "id") Long id) {
        Product getProduct = productRepository.findById(id).orElseThrow();
        ProductOrderDto productOrderDto = new ProductOrderDto(getProduct);
        return ResponseEntity.ok(productOrderDto);
    }

}
