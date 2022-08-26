package com.esmeralda.projects.microshopping.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductVo extends AtributoVo {
	private Integer id;
	private String name;
	private String description;
	private Integer quantity;
}
