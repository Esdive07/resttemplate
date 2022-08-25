package com.esmeralda.projects.microshopping.service;

import java.util.List;

import com.esmeralda.projects.microshopping.model.ShoppingVo;

public interface ShoppingService {

	public ShoppingVo createShopping(ShoppingVo shoppingVo);

	public List<ShoppingVo> getAllShopping();

	public ShoppingVo getShoppingById(Integer id);

	public ShoppingVo updateShopping(Integer id, ShoppingVo shoppingVo);

	public void deleteShopping(Integer id);

}
