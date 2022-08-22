package com.rah.demo.microproduct.service;

import java.util.List;
import java.util.Optional;

import com.rah.demo.microproduct.model.ProductVo;

public interface ProductService {

	public ProductVo createProduct(ProductVo productVo);

	public List<ProductVo> getAllProducts();

	public ProductVo updateProduct(ProductVo productVo, Integer id);

	public ProductVo updateQuality(Integer quantity, Integer id);

	public void delete(Integer id);

	public Optional<ProductVo> getProductById(Integer id);

}
