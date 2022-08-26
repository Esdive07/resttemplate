package com.esmeralda.projects.microshopping.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.esmeralda.projects.microshopping.entity.ShoppingEntity;
import com.esmeralda.projects.microshopping.model.ProductVo;
import com.esmeralda.projects.microshopping.model.ShoppingVo;
import com.esmeralda.projects.microshopping.repository.ShoppingRepository;
import com.esmeralda.projects.microshopping.service.ShoppingService;
import com.esmeralda.projects.microshopping.util.MapperUtil;

@Service
public class ShoppingServiceImpl implements ShoppingService {

	private ShoppingRepository shoppingRepository;
	private MapperUtil mapperUtil;
	private RestTemplate restTemplate;

	public ShoppingServiceImpl(ShoppingRepository shoppingRepository, MapperUtil mapperUtil) {
		super();
		this.shoppingRepository = shoppingRepository;
		this.mapperUtil = mapperUtil;
		this.restTemplate = new RestTemplate();
	}

	@Override
	public ShoppingVo createShopping(ShoppingVo shoppingVo) {

		if (shoppingVo.getQuantity() <= 0) {
			throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
		}

		ProductVo productVo = getproductId(shoppingVo);

		ShoppingEntity shoppingEntity = this.mapperUtil.mapperObject(shoppingVo, ShoppingEntity.class);
		ShoppingEntity shoppingSaved = this.shoppingRepository.save(shoppingEntity);

		updateProductByid(productVo, shoppingVo);

		return this.mapperUtil.mapperObject(shoppingSaved, ShoppingVo.class);
	}

	private Map<String, Integer> getUrlVariable(Integer id) {
		Map<String, Integer> urlVariable = new HashMap<>();
		urlVariable.put("id", id);
		return urlVariable;
	}

	public ProductVo getproductId(ShoppingVo shoppingVo) {
		ResponseEntity<ProductVo> response = this.restTemplate.getForEntity("http://localhost:8080/product/{id}",
				ProductVo.class, this.getUrlVariable(shoppingVo.getIdproduct()));

		ProductVo productVo = response.getBody();
		if (productVo == null) {
			throw new IllegalArgumentException("debe crear primero el producto");
		}

		return productVo;
	}

	public void updateProductByid(ProductVo productVo, ShoppingVo shoppingVo) {
		Integer quantity = productVo.getQuantity() + shoppingVo.getQuantity();
		productVo.setQuantity(quantity);
		productVo.setPrice(shoppingVo.getPrice());
		productVo.setSalePrice(shoppingVo.getSalePrice());
		this.restTemplate.put("http://localhost:8080/product/{id}", productVo,
				this.getUrlVariable(shoppingVo.getIdproduct()));
	}

	@Override
	public List<ShoppingVo> getAllShopping() {
		return this.shoppingRepository.findAll().stream()
				.map(value -> this.mapperUtil.mapperObject(value, ShoppingVo.class)).collect(Collectors.toList());
	}

	@Override
	public ShoppingVo getShoppingById(Integer id) {
		ShoppingEntity shoppingEntity = this.shoppingRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El id no existe"));
		return this.mapperUtil.mapperObject(shoppingEntity, ShoppingVo.class);
	}

	@Override
	public ShoppingVo updateShopping(Integer id, ShoppingVo shoppingVo) {
		ShoppingVo getshopping = this.getShoppingById(id);

		getshopping.setId(id);
		getshopping.setIdproduct(shoppingVo.getIdproduct());
		getshopping.setOrden(shoppingVo.getOrden());
		getshopping.setQuantity(shoppingVo.getQuantity());

		ShoppingEntity shoppingEntity = this.mapperUtil.mapperObject(getshopping, ShoppingEntity.class);
		ShoppingEntity shoppEntitySaved = this.shoppingRepository.save(shoppingEntity);
		return this.mapperUtil.mapperObject(shoppEntitySaved, ShoppingVo.class);
	}

	@Override
	public void deleteShopping(Integer id) {
		this.shoppingRepository.deleteById(id);

	}

}
