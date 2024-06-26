package com.maif.taskmanagerplus.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	INVALID_DATA("/invalid-data", "Invalid data"),
	ACCESS_DENIED("/access-denied", "Access denied"),
	ERROR_SYSTEM("/erro-system", "Erro of system"),
	INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
	INCOMPREHENSIBLE_MESSAGE("/incomprehensible-message", "Incomprehensible message"),
	RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
	ENTITY_IN_USE("/entity-in-use", "Entity in use"),
	ERROR_BUSINESS("/error-business", "Business rule violation"),
	ERROR_FORMAT("/error-format", "Inalid format"),
	MAX_LENGTH("/max-length", "Maximum length exceeded"), 
	INVALID_REQUEST_BODY("/invalid-request-body", "Invalid request body. Check the format of all fields and try again.");
	
		
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "http://localhost:8080" + path;
		this.title = title;
	}
	
}
