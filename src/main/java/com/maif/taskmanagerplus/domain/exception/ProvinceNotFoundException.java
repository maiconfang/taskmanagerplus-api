package com.maif.taskmanagerplus.domain.exception;

public class ProvinceNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ProvinceNotFoundException(String message) {
		super(message);
	}
	
	public ProvinceNotFoundException(Long provinceId) {
		this(String.format("There is no register of the province with a code %d", provinceId));
	}
	
}
