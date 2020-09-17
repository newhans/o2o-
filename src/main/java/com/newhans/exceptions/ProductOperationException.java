package com.newhans.exceptions;

public class ProductOperationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8628590908920275142L;
	
	public ProductOperationException(String msg) {
		super(msg);
	}
}
