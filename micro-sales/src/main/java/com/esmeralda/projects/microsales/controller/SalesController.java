package com.esmeralda.projects.microsales.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esmeralda.projects.microsales.model.SalesVo;
import com.esmeralda.projects.microsales.service.impl.SalesServiceImpl;

@RestController
@RequestMapping("/sales")
public class SalesController {

	private SalesServiceImpl salesServiceImpl;

	public SalesController(SalesServiceImpl salesServiceImpl) {
		super();
		this.salesServiceImpl = salesServiceImpl;
	}

	@PostMapping
	public ResponseEntity<SalesVo> createSale(@RequestBody SalesVo salesVo) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.salesServiceImpl.createSale(salesVo));
	}

	@GetMapping
	public ResponseEntity<List<SalesVo>> getAllSale() {
		return ResponseEntity.ok(this.salesServiceImpl.getAllSale());
	}

	@GetMapping("/{id}")
	public ResponseEntity<SalesVo> getSaleById(@PathVariable Integer id) {
		return ResponseEntity.ok(this.salesServiceImpl.getSaleById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<SalesVo> updateSale(@PathVariable Integer id, @RequestBody SalesVo salesVo) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.salesServiceImpl.updateSale(id, salesVo));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSale(@PathVariable Integer id) {
		this.salesServiceImpl.deleteSale(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
