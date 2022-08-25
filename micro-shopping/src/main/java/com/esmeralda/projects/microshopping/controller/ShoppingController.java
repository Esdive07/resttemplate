package com.esmeralda.projects.microshopping.controller;

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

import com.esmeralda.projects.microshopping.model.ShoppingVo;
import com.esmeralda.projects.microshopping.service.impl.ShoppingServiceImpl;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

	private ShoppingServiceImpl shoppingServiceImpl;

	public ShoppingController(ShoppingServiceImpl shoppingServiceImpl) {
		super();
		this.shoppingServiceImpl = shoppingServiceImpl;
	}

	@PostMapping
	public ResponseEntity<ShoppingVo> createShopping(@RequestBody ShoppingVo shoppingVo) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.shoppingServiceImpl.createShopping(shoppingVo));
	}

	@GetMapping
	public ResponseEntity<List<ShoppingVo>> getAllShopping() {
		return ResponseEntity.ok(this.shoppingServiceImpl.getAllShopping());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShoppingVo> getShoppingById(@PathVariable Integer id) {
		return ResponseEntity.ok(this.shoppingServiceImpl.getShoppingById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ShoppingVo> updateShopping(@PathVariable Integer id, @RequestBody ShoppingVo shoppingVo) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.shoppingServiceImpl.updateShopping(id, shoppingVo));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteShopping(@PathVariable Integer id) {
		this.shoppingServiceImpl.deleteShopping(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
