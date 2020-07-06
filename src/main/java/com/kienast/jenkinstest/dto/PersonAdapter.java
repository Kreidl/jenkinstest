package com.kienast.jenkinstest.dto;


import com.kienast.jenkinstest.model.Person;
import com.kienast.jenkinstest.rest.api.model.PersonModel;

public class PersonAdapter {
	
	private Person person;

	public PersonAdapter(Person person) {
		this.person = person;
	}

	public PersonModel createJson() {
		return new PersonModel().name(person.getName());
	}


}
