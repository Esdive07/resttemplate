package com.esmeralda.projects.microshopping.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShoppingVo extends AtributoVo {
	private Integer id;
	private Integer idproduct;
	private Integer quantity;
	private String orden;
}
