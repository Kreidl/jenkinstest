package com.kienast.jenkinstest.dto;

import com.kienast.jenkinstest.model.Person;
import com.kienast.jenkinstest.rest.api.model.PersonModel;

public class PeopleAdapter {
	
	private Person person;

	public PeopleAdapter(Person person) {
		this.person = person;
	}

	public PersonModel createJson() {
		return new PersonModel().name(person.getName());
	}
}
