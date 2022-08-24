package com.rah.demo.microproduct.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rah.demo.microproduct.entity.ProductEntity;
import com.rah.demo.microproduct.model.ProductVo;
import com.rah.demo.microproduct.repository.ProductRepository;
import com.rah.demo.microproduct.service.ProductService;
import com.rah.demo.microproduct.util.MapperUtil;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private MapperUtil mapperUtil;

	public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil) {
		super();
		this.productRepository = productRepository;
		this.mapperUtil = mapperUtil;
	}

	@Override
	public ProductVo createProduct(ProductVo productVo) {
		ProductEntity productEntity = this.mapperUtil.mapperObject(productVo, ProductEntity.class);
		ProductEntity saveProduct = this.productRepository.save(productEntity);
		return this.mapperUtil.mapperObject(saveProduct, ProductVo.class);
	}

	@Override
	public List<ProductVo> getAllProducts() {
		List<ProductVo> productList = this.productRepository.findAll().stream()
				.map(value -> this.mapperUtil.mapperObject(value, ProductVo.class)).collect(Collectors.toList());
		return productList;
	}

	@Override
	public ProductVo getProductById(Integer id) {
		ProductEntity productEntity = this.productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Id no existe"));
		return this.mapperUtil.mapperObject(productEntity, ProductVo.class);
	}

	@Override
	public ProductVo updateProduct(ProductVo productVo, Integer id) {
		ProductVo SavedproducVo = this.getProductById(id);
		SavedproducVo.setId(id);
		SavedproducVo.setName(productVo.getName());
		SavedproducVo.setPrice(productVo.getPrice());
		SavedproducVo.setQuantity(productVo.getQuantity());
		SavedproducVo.setSalePrice(productVo.getSalePrice());
		SavedproducVo.setDescription(productVo.getDescription());

		ProductEntity productEntity = this.mapperUtil.mapperObject(SavedproducVo, ProductEntity.class);
		ProductEntity prodEntity = this.productRepository.save(productEntity);
		return this.mapperUtil.mapperObject(prodEntity, ProductVo.class);
	}

	@Override
	public ProductVo updateQuality(Integer quantity, Integer id) {
		Optional<ProductEntity> optProductEntity = this.productRepository.findById(id);

//		ProductEntity productEntity = optProductEntity.map(value -> {
//			value.setQuantity(quantity);
//			return value;
//		}).orElseThrow(() -> new IllegalArgumentException("El id no existe"));

		ProductEntity productEntity = null;

		if (optProductEntity.isPresent()) {
			productEntity = optProductEntity.get();
			productEntity.setQuantity(quantity);
		} else {
			throw new IllegalArgumentException("El id no existe");
		}

		ProductEntity saveProduct = this.productRepository.save(productEntity);
		return this.mapperUtil.mapperObject(saveProduct, ProductVo.class);
	}

	@Override
	public void deleteProduct(Integer id) {
		this.productRepository.deleteById(id);
	}

}
