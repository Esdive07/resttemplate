package com.esmeralda.projects.microsales.model;

import lombok.Data;

@Data
public class ProductVo {
	private Integer id;
	private String name;
	private String description;
	private double price;
	private Integer quantity;
	private double salePrice;
}
