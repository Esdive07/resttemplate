package com.esmeralda.projects.microsales.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.esmeralda.projects.microsales.entity.SalesEntity;
import com.esmeralda.projects.microsales.model.ProductVo;
import com.esmeralda.projects.microsales.model.SalesVo;
import com.esmeralda.projects.microsales.repository.SalesRepository;
import com.esmeralda.projects.microsales.service.SalesService;
import com.esmeralda.projects.microsales.util.MapperUtil;

@Service
public class SalesServiceImpl implements SalesService {

	private SalesRepository salesRepository;
	private MapperUtil mapperUtil;
	private String hostUrlProductById = "http://localhost:8080/product/{id}";

	private RestTemplate restTemplate;

	public SalesServiceImpl(SalesRepository salesRepository, MapperUtil mapperUtil) {
		super();
		this.salesRepository = salesRepository;
		this.mapperUtil = mapperUtil;
		this.restTemplate = new RestTemplate();
	}

	@Override
	public SalesVo createSale(SalesVo salesVo) {
		/**
		 * 1.Consultar el producto por el id que recibio-->ok
		 * 
		 * 2.Validar que la cantidad que me piden existe del producto consultado en el
		 * punto 1-->ok
		 * 
		 * 3.con la cantidad recibida + precio del producto calcular el valor total-->ok
		 * 
		 * 4.validar que el producto no sea null-->ok
		 * 
		 * 5.despues que se crea la venta llamar al micro de productos y actualizar la
		 * cantidad de productos
		 */
		ProductVo productVo = getProductById(salesVo);

		SalesEntity salesEntity = this.mapperUtil.mapperObject(salesVo, SalesEntity.class);
		salesEntity.setTotal(productVo.getSalePrice() * salesVo.getQuality());

		SalesEntity SaveSale = this.salesRepository.save(salesEntity);

		updateProductById(salesVo, productVo);

		return this.mapperUtil.mapperObject(SaveSale, SalesVo.class);

	}

	private void updateProductById(SalesVo salesVo, ProductVo productVo) {
		Integer quantity = productVo.getQuantity() - salesVo.getQuality();
		productVo.setQuantity(quantity);
		this.restTemplate.put(hostUrlProductById, productVo, this.getUrlVariable(salesVo.getIdProduct()));
	}

	private ProductVo getProductById(SalesVo salesVo) {

		ResponseEntity<ProductVo> response = this.restTemplate.getForEntity(hostUrlProductById, ProductVo.class,
				this.getUrlVariable(salesVo.getIdProduct()));

		ProductVo productVo = response.getBody();

		if (productVo == null) {
			throw new IllegalArgumentException("El id del producto no Existe");
		}

		if (productVo.getQuantity() < salesVo.getQuality()) {
			throw new IllegalArgumentException("La cantidad solicitada supera el inventario");
		}
		return productVo;
	}

	private Map<String, Integer> getUrlVariable(Integer id) {
		Map<String, Integer> urlVariable = new HashMap<>();
		urlVariable.put("id", id);
		return urlVariable;
	}

	@Override
	public List<SalesVo> getAllSale() {
		return this.salesRepository.findAll().stream().map(value -> this.mapperUtil.mapperObject(value, SalesVo.class))
				.collect(Collectors.toList());
	}

	@Override
	public SalesVo getSaleById(Integer id) {

		SalesEntity salesEntity = this.salesRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El id no existe"));

		return this.mapperUtil.mapperObject(salesEntity, SalesVo.class);

	}

	@Override
	public SalesVo updateSale(Integer id, SalesVo salesVo) {
		SalesVo salVoSaved = this.getSaleById(id);
		salVoSaved.setId(id);
		salVoSaved.setIdProduct(salesVo.getIdProduct());
		salVoSaved.setQuality(salesVo.getQuality());

		SalesEntity salesEntity = this.mapperUtil.mapperObject(salVoSaved, SalesEntity.class);
		SalesEntity saleSaved = this.salesRepository.save(salesEntity);
		return this.mapperUtil.mapperObject(saleSaved, SalesVo.class);
	}

	@Override
	public void deleteSale(Integer id) {
		this.salesRepository.deleteById(id);

	}

}
