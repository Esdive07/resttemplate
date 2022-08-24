package com.esmeralda.projects.microsales.service;

import java.util.List;

import com.esmeralda.projects.microsales.model.SalesVo;

public interface SalesService {

	public SalesVo createSale(SalesVo salesVo);

	public List<SalesVo> getAllSale();

	public SalesVo updateSale(Integer id, SalesVo salesVo);

	public SalesVo getSaleById(Integer id);

	public void deleteSale(Integer id);

}
