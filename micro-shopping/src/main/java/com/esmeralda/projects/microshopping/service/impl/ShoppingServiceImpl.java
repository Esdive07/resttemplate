package com.esmeralda.projects.microshopping.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.esmeralda.projects.microshopping.entity.ShoppingEntity;
import com.esmeralda.projects.microshopping.model.ShoppingVo;
import com.esmeralda.projects.microshopping.repository.ShoppingRepository;
import com.esmeralda.projects.microshopping.service.ShoppingService;
import com.esmeralda.projects.microshopping.util.MapperUtil;

@Service
public class ShoppingServiceImpl implements ShoppingService {

	private ShoppingRepository shoppingRepository;
	private MapperUtil mapperUtil;

	public ShoppingServiceImpl(ShoppingRepository shoppingRepository, MapperUtil mapperUtil) {
		super();
		this.shoppingRepository = shoppingRepository;
		this.mapperUtil = mapperUtil;
	}

	@Override
	public ShoppingVo createShopping(ShoppingVo shoppingVo) {
		ShoppingEntity shoppingEntity = this.mapperUtil.mapperObject(shoppingVo, ShoppingEntity.class);
		ShoppingEntity shoppingSaved = this.shoppingRepository.save(shoppingEntity);
		return this.mapperUtil.mapperObject(shoppingSaved, ShoppingVo.class);
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
