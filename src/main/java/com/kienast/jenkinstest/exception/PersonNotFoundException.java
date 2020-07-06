package com.kienast.jenkinstest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "No surch Person")
public class PersonNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private final String id;

	public PersonNotFoundException(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
