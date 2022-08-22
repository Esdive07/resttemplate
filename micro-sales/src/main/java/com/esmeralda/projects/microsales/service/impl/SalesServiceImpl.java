package com.esmeralda.projects.microsales.service.impl;

import java.util.HashMap;
import java.util.Map;

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
		Map<String, Integer> urlVariable = new HashMap<>();
		urlVariable.put("id", salesVo.getIdProduct());

		ResponseEntity<ProductVo> response = this.restTemplate.getForEntity("http://localhost:8080/product/{id}",
				ProductVo.class, urlVariable);

		ProductVo productVo = response.getBody();

		if (productVo == null) {
			throw new IllegalArgumentException("El id del producto no Existe");
		}

		if (productVo.getQuantity() < salesVo.getQuality()) {
			throw new IllegalArgumentException("La cantidad solicitada supera el inventario");
		}

		SalesEntity salesEntity = this.mapperUtil.mapperObject(salesVo, SalesEntity.class);
		salesEntity.setTotal(productVo.getSalePrice() * salesVo.getQuality());

		SalesEntity SaveSale = this.salesRepository.save(salesEntity);
		
		
		// this.restTemplate.pos
		return this.mapperUtil.mapperObject(SaveSale, SalesVo.class);

	}

}
