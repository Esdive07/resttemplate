package com.rah.demo.microproduct.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rah.demo.microproduct.model.ProductVo;
import com.rah.demo.microproduct.service.impl.ProductServiceImpl;

@RestController
@RequestMapping("/product")
public class ProductController {

	private ProductServiceImpl productServiceImpl;

	public ProductController(ProductServiceImpl productServiceImpl) {
		super();
		this.productServiceImpl = productServiceImpl;
	}

	@PostMapping
	public ResponseEntity<ProductVo> createProduct(@RequestBody ProductVo productVo) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.productServiceImpl.createProduct(productVo));
	}

	@GetMapping
	public ResponseEntity<List<ProductVo>> getAllProducts() {
		return ResponseEntity.ok(this.productServiceImpl.getAllProducts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<ProductVo>> getProductById(@PathVariable Integer id) {
		return ResponseEntity.ok(this.productServiceImpl.getProductById(id));
	}

	// @patchmapping()--> actualiza un valor en especifico

	@PatchMapping("/{id}/{quantity}")
	public ResponseEntity<ProductVo> updateQuality(@PathVariable Integer id, @PathVariable Integer quantity) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.productServiceImpl.updateQuality(quantity, id));
	}
}
