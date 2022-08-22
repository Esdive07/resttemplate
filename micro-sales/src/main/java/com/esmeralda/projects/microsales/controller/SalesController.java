package com.esmeralda.projects.microsales.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
}
